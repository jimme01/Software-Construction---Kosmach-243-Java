"""Пакет music_streaming — демонстраційна бібліотека класів
до лабораторної роботи №1 (варіант 4: «Музичний стрімінг»).
"""

from .collections_ import Playlist, PlaylistPageIterator
from .context import PlaylistEditSession
from .decorators import validated
from .entities import Track
from .errors import MusicStreamingError, TrackNotFoundError
from .values import Duration, DurationDC

__all__ = [
    "Duration",
    "DurationDC",
    "Track",
    "Playlist",
    "PlaylistPageIterator",
    "validated",
    "PlaylistEditSession",
    "MusicStreamingError",
    "TrackNotFoundError",
]
