"""Об'єкти-значення (value objects) домену «музичний стрімінг».

Завдання 1: незмінний об'єкт-значення Duration — тривалість треку в секундах.
Завдання 4.2: арифметика над Duration (+, -, *, віддзеркалене *).
"""

from __future__ import annotations

from dataclasses import dataclass
from functools import total_ordering
from numbers import Real


@total_ordering
class Duration:
    """Незмінна тривалість (у цілих секундах).

    Незмінність забезпечується комбінацією __slots__ (жодного іншого
    атрибута додати не можна) та службового прапорця _frozen, який
    перевіряється у перевизначеному __setattr__: доки прапорець не
    встановлено (тобто під час __init__), присвоєння проходять напряму
    через object.__setattr__; після завершення __init__ будь-яке
    присвоєння підіймає AttributeError.
    """

    __slots__ = ("seconds", "_frozen")

    def __init__(self, seconds: int) -> None:
        seconds = int(seconds)
        if seconds < 0:
            raise ValueError("тривалість не може бути від'ємною")
        object.__setattr__(self, "seconds", seconds)
        object.__setattr__(self, "_frozen", True)

    def __setattr__(self, name: str, value: object) -> None:
        if getattr(self, "_frozen", False):
            raise AttributeError("об'єкт Duration незмінний")
        object.__setattr__(self, name, value)

    # ---- рядкові подання -------------------------------------------------
    def __repr__(self) -> str:
        return f"Duration(seconds={self.seconds})"

    def __str__(self) -> str:
        minutes, secs = divmod(self.seconds, 60)
        hours, minutes = divmod(minutes, 60)
        if hours:
            return f"{hours}:{minutes:02d}:{secs:02d}"
        return f"{minutes}:{secs:02d}"

    # ---- порівняння та впорядкування -------------------------------------
    def __eq__(self, other: object) -> bool:
        if not isinstance(other, Duration):
            return NotImplemented
        return self.seconds == other.seconds

    def __lt__(self, other: "Duration") -> bool:
        if not isinstance(other, Duration):
            return NotImplemented
        return self.seconds < other.seconds

    def __hash__(self) -> int:
        return hash(self.seconds)

    # ---- арифметика --------------------------------------------------------
    def __add__(self, other: "Duration") -> "Duration":
        if not isinstance(other, Duration):
            return NotImplemented
        return Duration(self.seconds + other.seconds)

    def __sub__(self, other: "Duration") -> "Duration":
        if not isinstance(other, Duration):
            return NotImplemented
        result = self.seconds - other.seconds
        if result < 0:
            raise ValueError("результат віднімання тривалостей не може бути від'ємним")
        return Duration(result)

    def __mul__(self, factor: float) -> "Duration":
        if not isinstance(factor, Real):
            return NotImplemented
        return Duration(round(self.seconds * factor))

    def __rmul__(self, factor: float) -> "Duration":
        return self.__mul__(factor)


@dataclass(frozen=True, slots=True, order=True)
class DurationDC:
    """Альтернативна реалізація того самого об'єкта-значення (завдання 1.2).

    @dataclass(frozen=True, slots=True) автоматично генерує __init__,
    __repr__, __eq__ та забороняє додавання нових атрибутів (slots) і
    зміну наявних (frozen). order=True додає __lt__/__le__/__gt__/__ge__
    на основі порівняння кортежу полів — тобто те саме, що й
    @functools.total_ordering у ручній реалізації вище.
    """

    seconds: int

    def __str__(self) -> str:
        minutes, secs = divmod(self.seconds, 60)
        return f"{minutes}:{secs:02d}"
