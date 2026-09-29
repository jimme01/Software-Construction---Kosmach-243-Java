"""Ліниві обчислення: генераторний конвеєр і власні генератори (завдання 6)."""
from __future__ import annotations

from collections.abc import Callable, Iterable, Iterator
from typing import Any

from .pipeline import Rec, aggregate, keep, normalize, parse


def g_parse(lines: Iterable[str]) -> Iterator[Rec]:
    for line in lines:
        rec = parse(line)
        if rec is not None:
            yield rec


def g_normalize(recs: Iterable[Rec]) -> Iterator[Rec]:
    for rec in recs:
        yield normalize(rec)


def g_keep(recs: Iterable[Rec], pred: Callable[[Rec], bool] = keep) -> Iterator[Rec]:
    for rec in recs:
        if pred(rec):
            yield rec


def run_lazy(lines: Iterable[str], pred: Callable[[Rec], bool] = keep) -> dict[str, int]:
    """Ланцюжок генераторів + агрегація (єдиний нелінивий крок)."""
    return aggregate(g_keep(g_normalize(g_parse(lines)), pred))


def record_stream() -> Iterator[Rec]:
    """Нескінченний потік записів у «сирій» формі (як після parse)."""
    metrics = ("Temp", "Humidity", "Pressure")
    n = 0
    while True:
        yield {
            "device": f" sensor-{n % 4:02d} ",
            "metric": f" {metrics[n % 3]} ",
            "value": str((n * 7) % 12),
            "ts": f"2024-01-01T00:{n % 60:02d}",
        }
        n += 1


# ---------- власні генератори (без itertools) ----------
def take(n: int, it: Iterable[Any]) -> Iterator[Any]:
    """Перші n елементів (зайвого елемента не споживає)."""
    if n <= 0:
        return
    for i, x in enumerate(it, 1):
        yield x
        if i >= n:
            return


def drop(n: int, it: Iterable[Any]) -> Iterator[Any]:
    """Усе, крім перших n елементів."""
    for i, x in enumerate(it):
        if i >= n:
            yield x
