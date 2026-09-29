"""demo.py — демонстрація роботи пакета music_streaming (варіант 4).

Запуск: python demo.py
"""

from __future__ import annotations

import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent / "src"))

from music_streaming import (  # noqa: E402
    Duration,
    DurationDC,
    MusicStreamingError,
    Playlist,
    PlaylistEditSession,
    Track,
    TrackNotFoundError,
)


def header(title: str) -> None:
    print(f"\n{'=' * 70}\n{title}\n{'=' * 70}")


# --------------------------------------------------------------------------
def demo_task1() -> None:
    header("Завдання 1. Незмінний об'єкт-значення Duration")

    a = Duration(225)
    b = Duration(225)
    print(f"a = {a!r} -> str: {a}")
    print(f"b = {b!r}")
    print(f"a is b -> {a is b}")
    print(f"a == b -> {a == b}")

    s = {a, b}
    print(f"set({{a, b}}) містить {len(s)} елемент(и), бо a == b і hash(a) == hash(b)")

    catalogue = {a: "перший запис", b: "другий запис (перезаписує перший)"}
    print(f"dict з ключем-Duration: {catalogue}")

    durations = [Duration(300), Duration(90), Duration(180)]
    print(f"sorted(durations) -> {sorted(durations)}")

    try:
        a.seconds = 999
    except AttributeError as exc:
        print(f"спроба a.seconds = 999 -> перехоплено AttributeError: {exc}")

    try:
        a.minutes = 5  # атрибута, якого немає в __slots__, взагалі не існує
    except AttributeError as exc:
        print(f"спроба a.minutes = 5 -> перехоплено AttributeError: {exc}")

    # 1.2 — альтернативна реалізація через dataclass(frozen=True, slots=True)
    dc1 = DurationDC(150)
    dc2 = DurationDC(150)
    print(f"\nDurationDC: {dc1!r}, dc1 == dc2 -> {dc1 == dc2}, dc1 < DurationDC(200) -> {dc1 < DurationDC(200)}")
    try:
        dc1.seconds = 1
    except Exception as exc:  # dataclasses.FrozenInstanceError є підкласом AttributeError
        print(f"спроба dc1.seconds = 1 -> перехоплено {type(exc).__name__}: {exc}")


# --------------------------------------------------------------------------
def demo_task2() -> None:
    header("Завдання 2. Доменна сутність Track: валідація через property")

    t = Track("TRK-1001", "Bohemian Rhapsody", "Queen", Duration(354), plays=1000)
    print(f"Створено коректний трек: {t}")
    print(f"repr: {t!r}")

    print("\n-- Перевірка обмежень при присвоєнні наявному об'єкту --")
    for action, fn in [
        ("t.title = ''  (порожній рядок)", lambda: setattr(t, "title", "  ")),
        ("t.artist = None", lambda: setattr(t, "artist", "")),
        ("t.duration = Duration(0)  (не додатна)", lambda: setattr(t, "duration", Duration(0))),
        ("t.plays = -5  (від'ємне)", lambda: setattr(t, "plays", -5)),
    ]:
        try:
            fn()
        except ValueError as exc:
            print(f"{action} -> ValueError: {exc}")

    print("\n-- Ті самі порушення, але вже в конструкторі --")
    try:
        Track("TRK-2002", "", "Someone", Duration(120))
    except ValueError as exc:
        print(f"Track(title='') -> ValueError: {exc}")

    try:
        Track("bad id", "Title", "Artist", Duration(120))
    except ValueError as exc:
        print(f"Track(track_id='bad id') -> ValueError: {exc}")

    print("\n-- classmethod from_dict --")
    good = Track.from_dict(
        {"track_id": "TRK-3003", "title": "Hotel California", "artist": "Eagles", "duration": 391, "plays": 500}
    )
    print(f"from_dict (коректний словник) -> {good}")
    try:
        Track.from_dict({"track_id": "TRK-4004", "title": "X", "artist": "Y", "duration": -10})
    except ValueError as exc:
        print(f"from_dict (некоректний словник, duration=-10) -> ValueError: {exc}")

    print("\n-- staticmethod is_valid_track_id --")
    print(f"Track.is_valid_track_id('TRK-1001') -> {Track.is_valid_track_id('TRK-1001')}")
    print(f"Track.is_valid_track_id('1001')     -> {Track.is_valid_track_id('1001')}")


# --------------------------------------------------------------------------
def _make_sample_playlist() -> Playlist:
    return Playlist(
        [
            Track("TRK-0001", "Bohemian Rhapsody", "Queen", Duration(354), plays=1000),
            Track("TRK-0002", "Another One Bites the Dust", "Queen", Duration(215), plays=800),
            Track("TRK-0003", "Hotel California", "Eagles", Duration(391), plays=1200),
            Track("TRK-0004", "Yesterday", "The Beatles", Duration(125), plays=2000),
            Track("TRK-0005", "Billie Jean", "Michael Jackson", Duration(294), plays=1500),
        ]
    )


def demo_task3() -> None:
    header("Завдання 3. Колекція Playlist: протоколи доступу та обходу")

    playlist = _make_sample_playlist()
    print(f"len(playlist) -> {len(playlist)}")
    print(f"playlist[0] -> {playlist[0]}")

    sliced = playlist[0:2]
    print(f"playlist[0:2] -> тип {type(sliced).__name__}, вміст: {list(sliced)}")

    print(f"'TRK-0003' in playlist -> {'TRK-0003' in playlist}")
    print(f"'TRK-9999' in playlist -> {'TRK-9999' in playlist}")

    print("\n-- обхід for ... in playlist --")
    for track in playlist:
        print(f"  {track}")

    print("\n-- посторінковий обхід pages(2) --")
    for i, page in enumerate(playlist.pages(2), start=1):
        print(f"  сторінка {i}: {[t.title for t in page]}")

    print("\n-- __call__: фільтрація за критеріями --")
    queen_tracks = playlist(artist="Queen")
    print(f"playlist(artist='Queen') -> {[t.title for t in queen_tracks]}")
    long_tracks = playlist(min_duration=300)
    print(f"playlist(min_duration=300) -> {[t.title for t in long_tracks]}")
    try:
        playlist(unknown_field=1)
    except TypeError as exc:
        print(f"playlist(unknown_field=1) -> TypeError: {exc}")

    print("\n-- EAFP-пошук get() --")
    print(f"playlist.get('TRK-0004') -> {playlist.get('TRK-0004')}")
    try:
        playlist.get("TRK-9999")
    except TrackNotFoundError as exc:
        print(f"playlist.get('TRK-9999') -> {type(exc).__name__}: {exc}")


# --------------------------------------------------------------------------
def demo_task4() -> None:
    header("Завдання 4. Перевантаження операторів")

    a = Playlist([Track("TRK-0001", "Bohemian Rhapsody", "Queen", Duration(354))])
    b = Playlist([Track("TRK-0002", "Yesterday", "The Beatles", Duration(125))])
    c = Playlist([Track("TRK-0001", "Bohemian Rhapsody (дублікат id)", "Queen", Duration(354))])

    combined = a + b
    print(f"a + b -> {[t.title for t in combined]} (довжина {len(combined)})")

    combined_dup = a + c
    print(f"a + c (той самий track_id) -> {[t.title for t in combined_dup]} (довжина {len(combined_dup)}, дублікат відкинуто)")

    total = sum([a, b, c])
    print(f"sum([a, b, c]) -> {[t.title for t in total]} (довжина {len(total)})")

    try:
        a + 5  # type: ignore[operator]
    except TypeError as exc:
        print(f"a + 5 -> перехоплено TypeError: {exc}")

    print("\n-- арифметика Duration --")
    d1, d2 = Duration(90), Duration(45)
    print(f"d1 + d2 -> {(d1 + d2)!r}")
    print(f"d1 - d2 -> {(d1 - d2)!r}")
    print(f"d1 * 2  -> {(d1 * 2)!r}")
    print(f"2 * d1  -> {(2 * d1)!r}")
    try:
        d1 + 10  # type: ignore[operator]
    except TypeError as exc:
        print(f"d1 + 10 -> перехоплено TypeError: {exc}")

    durations = [Duration(300), Duration(90), Duration(180)]
    print(f"min/max/sorted(durations) -> {min(durations)!r} / {max(durations)!r} / {sorted(durations)}")


# --------------------------------------------------------------------------
def demo_task5() -> None:
    header("Завдання 5. Валідаційний декоратор @validated")

    playlist = Playlist()
    playlist.add_track(track_id="TRK-9001", title="New Track", artist="Some Artist", duration_seconds=200)
    print(f"add_track з коректними аргументами -> {[t.title for t in playlist]}")

    for kwargs, label in [
        (dict(track_id="TRK-9002", title="", artist="A", duration_seconds=100), "порожній title"),
        (dict(track_id="TRK-9003", title="T", artist="  ", duration_seconds=100), "порожній artist"),
        (dict(track_id="TRK-9004", title="T", artist="A", duration_seconds=-5), "duration_seconds <= 0"),
    ]:
        try:
            playlist.add_track(**kwargs)
        except ValueError as exc:
            print(f"add_track ({label}) -> перехоплено ValueError: {exc}")

    try:
        playlist.rename_track(track_id="TRK-9001", new_title="   ")
    except ValueError as exc:
        print(f"rename_track (порожній new_title) -> перехоплено ValueError: {exc}")

    playlist.rename_track(track_id="TRK-9001", new_title="Renamed Track")
    print(f"rename_track (коректно) -> {playlist.get('TRK-9001').title}")

    print(f"\nPlaylist.add_track.__name__    -> {Playlist.add_track.__name__!r}")
    print(f"Playlist.rename_track.__name__ -> {Playlist.rename_track.__name__!r}")


# --------------------------------------------------------------------------
def demo_task6() -> None:
    header("Завдання 6. Контекстний менеджер PlaylistEditSession")

    playlist = _make_sample_playlist()
    before = [t.track_id for t in playlist]
    print(f"стан до сеансу: {before}")

    print("\n-- успішний сеанс --")
    with PlaylistEditSession(playlist) as p:
        p.add_track(track_id="TRK-0006", title="Imagine", artist="John Lennon", duration_seconds=183)
        p.add_track(track_id="TRK-0007", title="Let It Be", artist="The Beatles", duration_seconds=243)
    print(f"стан після успішного сеансу: {[t.track_id for t in playlist]}")

    print("\n-- сеанс, перерваний винятком --")
    state_before_failed_session = [t.track_id for t in playlist]
    try:
        with PlaylistEditSession(playlist) as p:
            p.add_track(track_id="TRK-0008", title="Temporary 1", artist="X", duration_seconds=100)
            p.add_track(track_id="TRK-0009", title="Temporary 2", artist="Y", duration_seconds=100)
            raise RuntimeError("імітація збою під час пакетних змін")
    except RuntimeError as exc:
        print(f"виняток дійшов до місця виклику: {exc}")

    state_after_failed_session = [t.track_id for t in playlist]
    print(f"стан до перерваного сеансу:   {state_before_failed_session}")
    print(f"стан після перерваного сеансу: {state_after_failed_session}")
    print(f"стани однакові (обидва додавання скасовано) -> {state_before_failed_session == state_after_failed_session}")


# --------------------------------------------------------------------------
def main() -> None:
    demo_task1()
    demo_task2()
    demo_task3()
    demo_task4()
    demo_task5()
    demo_task6()
    print("\nГотово: усі сценарії відпрацьовано без непередбачених винятків.")


if __name__ == "__main__":
    main()
