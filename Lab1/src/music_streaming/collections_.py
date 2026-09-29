"""Завдання 3–5: колекція сутностей Playlist.

Реалізує протокол послідовності (__len__/__getitem__/__contains__/__iter__),
посторінковий обхід, фільтрацію викликом екземпляра (__call__),
пошук у стилі EAFP, оператори + / sum(), та два методи, обгорнуті
валідаційним декоратором @validated.
"""

from __future__ import annotations

from typing import Any, Iterable, Iterator

from .decorators import validated
from .entities import Track
from .errors import TrackNotFoundError
from .values import Duration

_SUPPORTED_QUERIES = ("artist", "min_duration")


class PlaylistPageIterator:
    """Окремий об'єкт-ітератор для посторінкового обходу Playlist (завдання 3.2).

    Тримає посилання на той самий плейлист (елементи не копіюються) і
    поточний індекс; звертається до __len__/__getitem__ самого плейлиста.
    """

    def __init__(self, playlist: "Playlist", page_size: int) -> None:
        if page_size <= 0:
            raise ValueError("page_size повинен бути додатним")
        self._playlist = playlist
        self._page_size = page_size
        self._index = 0

    def __iter__(self) -> "PlaylistPageIterator":
        return self

    def __next__(self) -> list[Track]:
        if self._index >= len(self._playlist):
            raise StopIteration
        page = [
            self._playlist[i]
            for i in range(self._index, min(self._index + self._page_size, len(self._playlist)))
        ]
        self._index += self._page_size
        return page


class Playlist:
    """Колекція треків із протоколами доступу й обходу."""

    def __init__(self, tracks: Iterable[Track] = ()) -> None:
        self._tracks: list[Track] = list(tracks)
        self._index: dict[str, Track] = {t.track_id: t for t in self._tracks}

    # ---- протокол послідовності (3.1) --------------------------------------
    def __len__(self) -> int:
        return len(self._tracks)

    def __getitem__(self, key: int | slice) -> "Track | Playlist":
        if isinstance(key, slice):
            return Playlist(self._tracks[key])
        return self._tracks[key]

    def __contains__(self, item: "Track | str") -> bool:
        track_id = item.track_id if isinstance(item, Track) else item
        return track_id in self._index

    def __iter__(self) -> Iterator[Track]:
        return iter(self._tracks)

    def __repr__(self) -> str:
        return f"Playlist({self._tracks!r})"

    # ---- посторінковий обхід (3.2) -----------------------------------------
    def pages(self, page_size: int) -> PlaylistPageIterator:
        return PlaylistPageIterator(self, page_size)

    # ---- виклик екземпляра як запит (3.3) ----------------------------------
    def __call__(self, **criteria: Any) -> "Playlist":
        unknown = set(criteria) - set(_SUPPORTED_QUERIES)
        if unknown:
            raise TypeError(
                f"невідомі критерії запиту {sorted(unknown)}; "
                f"припустимі: {_SUPPORTED_QUERIES}"
            )
        result = list(self._tracks)
        if "artist" in criteria:
            wanted = criteria["artist"]
            result = [t for t in result if t.artist == wanted]
        if "min_duration" in criteria:
            threshold = criteria["min_duration"]
            threshold_seconds = (
                threshold.seconds if isinstance(threshold, Duration) else int(threshold)
            )
            result = [t for t in result if t.duration.seconds >= threshold_seconds]
        return Playlist(result)

    # ---- пошук за ідентифікатором у стилі EAFP (3.4) -----------------------
    def get(self, track_id: str) -> Track:
        try:
            return self._index[track_id]
        except KeyError:
            raise TrackNotFoundError(track_id) from None

    # ---- оператори (4.1) ----------------------------------------------------
    def __add__(self, other: "Playlist") -> "Playlist":
        if not isinstance(other, Playlist):
            return NotImplemented
        merged = {t.track_id: t for t in self._tracks}
        for track in other:
            merged.setdefault(track.track_id, track)
        return Playlist(merged.values())

    def __radd__(self, other: Any) -> "Playlist":
        if other == 0:
            return Playlist(self._tracks)
        return self.__add__(other)

    # ---- мутатори, обгорнуті валідаційним декоратором (5.1) -----------------
    @validated(title="non_empty", artist="non_empty", duration_seconds="positive")
    def add_track(
        self,
        *,
        track_id: str,
        title: str,
        artist: str,
        duration_seconds: int,
        plays: int = 0,
    ) -> Track:
        track = Track(
            track_id=track_id,
            title=title,
            artist=artist,
            duration=Duration(duration_seconds),
            plays=plays,
        )
        self._tracks.append(track)
        self._index[track.track_id] = track
        return track

    @validated(new_title="non_empty")
    def rename_track(self, *, track_id: str, new_title: str) -> None:
        track = self.get(track_id)
        track.title = new_title

    # ---- для внутрішнього використання контекстним менеджером (завд. 6) ----
    def _snapshot(self) -> tuple[list[Track], dict[str, Track]]:
        import copy

        return copy.deepcopy(self._tracks), copy.deepcopy(self._index)

    def _restore(self, snapshot: tuple[list[Track], dict[str, Track]]) -> None:
        self._tracks, self._index = snapshot
