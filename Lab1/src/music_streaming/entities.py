"""Доменна сутність домену «музичний стрімінг».

Завдання 2: Track — трек у стрімінговому сервісі, з валідацією полів
через property та допоміжними class/staticmethod.
"""

from __future__ import annotations

import re
from typing import Any

from .values import Duration

_TRACK_ID_PATTERN = re.compile(r"^[A-Za-z]+-\d+$")  # напр. "TRK-1024"


class Track:
    """Трек: track_id, title, artist, duration, plays."""

    def __init__(
        self,
        track_id: str,
        title: str,
        artist: str,
        duration: Duration,
        plays: int = 0,
    ) -> None:
        if not self.is_valid_track_id(track_id):
            raise ValueError(
                f"track_id повинен мати формат «Літери-Число», отримано {track_id!r}"
            )
        self._track_id = track_id
        # поля з обмеженнями присвоюються через сетери — валідація
        # спрацьовує однаково і в конструкторі, і при подальшій зміні.
        self.title = title
        self.artist = artist
        self.duration = duration
        self.plays = plays

    # ---- track_id: незмінний ідентифікатор, лише читання -----------------
    @property
    def track_id(self) -> str:
        return self._track_id

    # ---- title -------------------------------------------------------------
    @property
    def title(self) -> str:
        return self._title

    @title.setter
    def title(self, value: str) -> None:
        if not isinstance(value, str) or not value.strip():
            raise ValueError("назва треку (title) не може бути порожньою")
        self._title = value.strip()

    # ---- artist --------------------------------------------------------------
    @property
    def artist(self) -> str:
        return self._artist

    @artist.setter
    def artist(self, value: str) -> None:
        if not isinstance(value, str) or not value.strip():
            raise ValueError("виконавець (artist) не може бути порожнім")
        self._artist = value.strip()

    # ---- duration --------------------------------------------------------------
    @property
    def duration(self) -> Duration:
        return self._duration

    @duration.setter
    def duration(self, value: Duration) -> None:
        if not isinstance(value, Duration):
            raise ValueError("duration повинен бути об'єктом Duration")
        if value.seconds <= 0:
            raise ValueError("тривалість треку (duration) повинна бути додатною")
        self._duration = value

    # ---- plays -------------------------------------------------------------
    @property
    def plays(self) -> int:
        return self._plays

    @plays.setter
    def plays(self, value: int) -> None:
        if not isinstance(value, int) or isinstance(value, bool) or value < 0:
            raise ValueError("кількість прослуховувань (plays) не може бути від'ємною")
        self._plays = value

    # ---- метод класу та статичний метод -----------------------------------
    @classmethod
    def from_dict(cls, data: dict[str, Any]) -> "Track":
        """Будує Track зі словника «сирих» значень із тими самими перевірками."""
        duration_raw = data["duration"]
        duration = duration_raw if isinstance(duration_raw, Duration) else Duration(duration_raw)
        return cls(
            track_id=data["track_id"],
            title=data["title"],
            artist=data["artist"],
            duration=duration,
            plays=data.get("plays", 0),
        )

    @staticmethod
    def is_valid_track_id(value: str) -> bool:
        """Перевіряє формат ідентифікатора треку («Літери-Число»)."""
        return isinstance(value, str) and bool(_TRACK_ID_PATTERN.match(value))

    # ---- рядкове подання (зручно для демонстрації, не вимагається завданням) --
    def __repr__(self) -> str:
        return (
            f"Track(track_id={self.track_id!r}, title={self.title!r}, "
            f"artist={self.artist!r}, duration={self.duration!r}, plays={self.plays})"
        )

    def __str__(self) -> str:
        return f"{self.artist} — {self.title} ({self.duration})"
