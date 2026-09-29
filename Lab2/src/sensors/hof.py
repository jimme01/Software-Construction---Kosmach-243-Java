"""Функції вищого порядку, замикання, partial, каррирування (завдання 3, 5)."""
from __future__ import annotations

import operator
from collections.abc import Callable
from functools import partial, reduce
from typing import Any

Rec = dict[str, Any]


# ---------- 3.1 композиція ----------
def identity(x: Any) -> Any:
    return x


def compose2(f: Callable, g: Callable) -> Callable:
    """x -> f(g(x))"""
    return lambda x: f(g(x))


def compose(*funcs: Callable) -> Callable:
    """Справа наліво: compose(f, g, h)(x) == f(g(h(x)))."""
    return reduce(compose2, funcs, identity)


def pipe(*funcs: Callable) -> Callable:
    """Зліва направо: pipe(f, g, h)(x) == h(g(f(x)))."""
    return compose(*reversed(funcs))


def strip_strings(rec: Rec) -> Rec:
    return {k: v.strip() if isinstance(v, str) else v for k, v in rec.items()}


def lower_category(rec: Rec) -> Rec:
    return {**rec, "metric": rec["metric"].lower()}


def to_int_number(rec: Rec) -> Rec:
    return {**rec, "value": int(rec["value"])}


# ---------- 3.2 список функцій як дані ----------
STEPS: list[Callable[[Rec], Rec]] = [strip_strings, lower_category, to_int_number]

normalize_piped: Callable[[Rec], Rec] = pipe(*STEPS)


def normalize_loop(rec: Rec) -> Rec:
    for step in STEPS:
        rec = step(rec)
    return rec


# ---------- 3.3 замикання ----------
def make_predicate(field: str, op: str, value: Any) -> Callable[[Rec], bool]:
    """Замикає field, op, value; лише читає їх (nonlocal не потрібен)."""
    fn = getattr(operator, op)

    def predicate(rec: Rec) -> bool:
        return fn(rec[field], value)

    return predicate


def make_running_total() -> Callable[[float], float]:
    """Замикає накопичувач total; змінює його через nonlocal."""
    total = 0

    def add(x: float) -> float:
        nonlocal total
        total += x
        return total

    return add


classify: Callable[[int], str] = lambda v: "мало" if v < 5 else "звичайно" if v < 20 else "багато"


# ---------- 5.1 partial ----------
def scale(factor: float, value: float) -> float:
    return round(value * factor, 3)


to_km = partial(scale, 1.609344)     # милі -> км
to_miles = partial(scale, 0.621371)  # км -> милі

# фіксуємо перші два аргументи make_predicate, лишається лише поріг
keep_ge_value = partial(make_predicate, "value", "ge")


# ---------- 5.2 каррирування ----------
def curry3(f: Callable) -> Callable:
    """curry3(f)(a)(b)(c) == f(a, b, c)."""
    return lambda a: lambda b: lambda c: f(a, b, c)


curried_predicate = curry3(make_predicate)  # pred("value")("ge")(5)
