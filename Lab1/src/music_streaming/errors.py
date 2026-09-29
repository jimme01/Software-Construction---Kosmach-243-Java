"""Доменні винятки пакета music_streaming."""

from __future__ import annotations


class MusicStreamingError(Exception):
    """Базовий клас усіх винятків домену."""


class TrackNotFoundError(MusicStreamingError, KeyError):
    """Трек із заданим ідентифікатором відсутній у колекції."""

    def __init__(self, track_id: str) -> None:
        self.track_id = track_id
        super().__init__(f"трек з ідентифікатором {track_id!r} не знайдено")
