"""Рекурсивний обхід дерева."""
from __future__ import annotations

from .model import Node


def total(node: Node) -> int:
    """Сума value у вузлі та в усіх нащадках.

    База: вузол без нащадків -> його власне value (рекурсія зупиняється).
    Крок: value вузла + total кожного нащадка.
    """
    if not node.children:
        return node.value
    return node.value + sum(total(child) for child in node.children)


def depth(node: Node) -> int:
    """Глибина дерева (лист = 1)."""
    if not node.children:
        return 1
    return 1 + max(depth(child) for child in node.children)
