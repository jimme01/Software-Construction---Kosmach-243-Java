"""Завдання 5: валідаційний декоратор з параметрами."""

from __future__ import annotations

from functools import wraps
from typing import Any, Callable, TypeVar

F = TypeVar("F", bound=Callable[..., Any])


def _check_rule(name: str, value: Any, rule: str) -> None:
    if rule == "positive":
        if not isinstance(value, (int, float)) or isinstance(value, bool) or value <= 0:
            raise ValueError(f"аргумент {name!r} повинен бути додатним числом")
    elif rule == "non_empty":
        if not isinstance(value, str) or not value.strip():
            raise ValueError(f"аргумент {name!r} не може бути порожнім рядком")
    elif rule.startswith("one_of:"):
        allowed = rule.removeprefix("one_of:").split(",")
        if value not in allowed:
            raise ValueError(f"аргумент {name!r} повинен бути одним з {allowed}")
    else:
        raise ValueError(f"невідоме правило валідації: {rule!r}")


def validated(**rules: str) -> Callable[[F], F]:
    """Декоратор із параметрами: validated(**rules).

    Складається з трьох рівнів вкладених функцій:
      1. validated(**rules)      — приймає параметри декоратора і повертає...
      2. decorator(func)         — приймає обгортувану функцію і повертає...
      3. wrapper(*args, **kwargs) — власне обгортку, що виконує перевірку
                                     перед викликом func.
    functools.wraps(func) на wrapper зберігає __name__/__doc__ оригіналу,
    тож StarSystem.add_planet.__name__ (тут — Playlist.add_track.__name__
    тощо) лишається справжнім ім'ям методу, а не "wrapper".
    """

    def decorator(func: F) -> F:
        @wraps(func)
        def wrapper(*args: Any, **kwargs: Any) -> Any:
            for arg_name, rule in rules.items():
                if arg_name in kwargs:
                    _check_rule(arg_name, kwargs[arg_name], rule)
            return func(*args, **kwargs)

        return wrapper  # type: ignore[return-value]

    return decorator
