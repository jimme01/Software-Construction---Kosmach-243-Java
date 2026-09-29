"""Калькулятор виразів на match / case (завдання 7 у тексті, «8» у нумерації методички)."""
from __future__ import annotations

import math
from dataclasses import dataclass


@dataclass(frozen=True)
class Num: value: float
@dataclass(frozen=True)
class Neg: x: "Expr"
@dataclass(frozen=True)
class Add: a: "Expr"; b: "Expr"
@dataclass(frozen=True)
class Sub: a: "Expr"; b: "Expr"
@dataclass(frozen=True)
class Mul: a: "Expr"; b: "Expr"
@dataclass(frozen=True)
class Div: a: "Expr"; b: "Expr"
@dataclass(frozen=True)
class Pow: base: "Expr"; exp: int
@dataclass(frozen=True)
class Sqrt: x: "Expr"
@dataclass(frozen=True)
class Sum: terms: tuple["Expr", ...]


Expr = Num | Neg | Add | Sub | Mul | Div | Pow | Sqrt | Sum


def evaluate(node: Expr) -> float:
    match node:
        case Num(0):                      # літеральний патерн
            return 0.0
        case Num(v):
            return float(v)
        case Neg(x):                      # патерн класу з розкладанням
            return -evaluate(x)
        case Add(a, b):
            return evaluate(a) + evaluate(b)
        case Sub(a, b):
            return evaluate(a) - evaluate(b)
        case Mul(a, b):
            return evaluate(a) * evaluate(b)
        case Div(a, b):
            divisor = evaluate(b)
            if divisor == 0:
                raise ValueError("ділення на нуль")
            return evaluate(a) / divisor
        case Pow(base, exp) if exp >= 0:  # guard-умова
            return evaluate(base) ** exp
        case Pow():
            raise ValueError("від'ємний показник")
        case Sqrt(x):
            value = evaluate(x)
            if value < 0:
                raise ValueError("корінь із від'ємного числа")
            return math.sqrt(value)
        case Sum(()):                     # порожня послідовність — перед загальною
            return 0.0
        case Sum((first, *rest)):         # розпакування: голова + хвіст
            return evaluate(first) + evaluate(Sum(tuple(rest)))
        case _:
            raise ValueError(f"невідомий вузол: {node!r}")
