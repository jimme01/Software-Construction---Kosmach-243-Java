"""Завдання 6: контекстний менеджер пакетних (атомарних) змін Playlist."""

from __future__ import annotations

from types import TracebackType
from typing import Optional, Type

from .collections_ import Playlist


class PlaylistEditSession:
    """Сеанс атомарних пакетних змін плейліста.

    __enter__ знімає знімок поточного стану плейліста (copy.deepcopy) і
    повертає сам плейліст для роботи всередині блоку with. Якщо блок
    завершується без винятку — зміни лишаються застосованими. Якщо виникає
    виняток — стан плейліста відновлюється зі знімка, а сам виняток не
    приховується (метод повертає False, тож він летить далі).
    """

    def __init__(self, playlist: Playlist) -> None:
        self._playlist = playlist
        self._snapshot: tuple | None = None

    def __enter__(self) -> Playlist:
        self._snapshot = self._playlist._snapshot()
        return self._playlist

    def __exit__(
        self,
        exc_type: Optional[Type[BaseException]],
        exc: Optional[BaseException],
        traceback: Optional[TracebackType],
    ) -> bool:
        if exc_type is not None:
            assert self._snapshot is not None
            self._playlist._restore(self._snapshot)
        return False
