"""parse / normalize / keep / aggregate та конвеєр (завдання 1, 4)."""
from __future__ import annotations

from collections.abc import Callable, Iterable
from dataclasses import dataclass
from functools import reduce
from typing import Any

Rec = dict[str, Any]
DEFAULT_THRESHOLD = 5


@dataclass(frozen=True)
class Result:
    records: tuple[Rec, ...]
    errors: int


def parse(line: str) -> Rec | None:
    """Рядок 'device;metric;value;ts' -> dict або None, якщо некоректний."""
    parts = line.rstrip("\r\n").split(";")
    if len(parts) != 4:
        return None
    device, metric, value, ts = parts
    if not (device.strip() and metric.strip() and ts.strip()):
        return None
    try:
        int(value.strip())
    except ValueError:
        return None
    return {"device": device, "metric": metric, "value": value, "ts": ts}


def normalize(rec: Rec) -> Rec:
    """strip для рядків, lower для metric, int для value; решта без змін."""
    return {
        **rec,
        "device": rec["device"].strip(),
        "metric": rec["metric"].strip().lower(),
        "value": int(rec["value"]),
        "ts": rec["ts"].strip(),
    }


def keep(rec: Rec, threshold: int = DEFAULT_THRESHOLD) -> bool:
    """Залишає записи з value >= threshold."""
    return rec["value"] >= threshold


def aggregate(records: Iterable[Rec]) -> dict[str, int]:
    """Групує за metric і підсумовує value."""
    totals: dict[str, int] = {}
    for rec in records:
        totals[rec["metric"]] = totals.get(rec["metric"], 0) + rec["value"]
    return totals


def process(lines: Iterable[str], now: float) -> Result:
    """Чиста функція: нічого не відкриває, не друкує, не читає час."""
    parsed = [parse(line) for line in lines]
    records = tuple({**r, "received_at": now} for r in parsed if r is not None)
    return Result(records=records, errors=parsed.count(None))


def add_to_groups(acc: dict[str, int], rec: Rec) -> dict[str, int]:
    """Акумулятор для reduce; повертає НОВИЙ словник (acc не мутується)."""
    return {**acc, rec["metric"]: acc.get(rec["metric"], 0) + rec["value"]}


def run_mfr(lines: Iterable[str], pred: Callable[[Rec], bool] = keep) -> dict[str, int]:
    """Спосіб A: map / filter / reduce."""
    parsed = filter(lambda r: r is not None, map(parse, lines))
    normalized = map(normalize, parsed)
    kept = filter(pred, normalized)
    return reduce(add_to_groups, kept, {})


def run_comp(lines: Iterable[str], pred: Callable[[Rec], bool] = keep) -> dict[str, int]:
    """Спосіб B: спискові включення."""
    parsed = [r for line in lines if (r := parse(line)) is not None]
    normalized = [normalize(r) for r in parsed]
    kept = [r for r in normalized if pred(r)]
    return aggregate(kept)
