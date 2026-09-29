"""Незмінні типи даних: Record (завдання 2) і Node дерева (рекурсія)."""
from __future__ import annotations

from dataclasses import dataclass, replace
from typing import Any


@dataclass(frozen=True, slots=True)
class Record:
    device: str
    metric: str
    value: int
    ts: str
    received_at: float


@dataclass  # без frozen — для демонстрації нехешованості
class MutableRecord:
    device: str
    metric: str
    value: int
    ts: str
    received_at: float


def to_record(d: dict[str, Any]) -> Record:
    """Нормалізований словник (із received_at) -> Record."""
    return Record(
        device=d["device"],
        metric=d["metric"],
        value=d["value"],
        ts=d["ts"],
        received_at=d["received_at"],
    )


def add_to_value(rec: Record, delta: int) -> Record:
    """Новий запис зі збільшеним числовим полем; rec не змінюється."""
    return replace(rec, value=rec.value + delta)


def with_device(rec: Record, device: str) -> Record:
    """Новий запис із заміненим рядковим полем device."""
    return replace(rec, device=device)


@dataclass(frozen=True)
class Node:
    """Вузол дерева: об'єкт (цех/пристрій) зі значенням і нащадками."""
    name: str
    value: int = 0
    children: tuple[Node, ...] = ()
