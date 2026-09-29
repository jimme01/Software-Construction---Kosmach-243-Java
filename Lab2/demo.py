"""Демонстрація ЛР №2, варіант 4: події сенсорів (device;metric;value;ts)."""
import sys
import time
from dataclasses import FrozenInstanceError
from itertools import islice
from pathlib import Path

ROOT = Path(__file__).parent
sys.path.insert(0, str(ROOT / "src"))
DATA = ROOT / "data" / "sensors.txt"

from sensors.calculator import Add, Div, Mul, Neg, Num, Pow, Sqrt, Sub, Sum, evaluate  # noqa: E402
from sensors.hof import (  # noqa: E402
    classify, compose, curried_predicate, curry3, keep_ge_value, make_predicate,
    make_running_total, normalize_loop, normalize_piped, pipe, to_km, to_miles,
)
from sensors.lazy import (  # noqa: E402
    drop, g_keep, g_normalize, g_parse, record_stream, run_lazy,
    take,
)
from sensors.model import (  # noqa: E402
    MutableRecord, Node, Record, add_to_value, to_record, with_device,
)
from sensors.pipeline import keep, normalize, parse, process, run_comp, run_mfr  # noqa: E402
from sensors.recursion import depth, total  # noqa: E402


def title(text: str) -> None:
    print(f"\n=== {text} ===")


def check(label: str, cond: bool) -> None:
    print(f"  [{'OK' if cond else 'FAIL'}] {label}")


def main() -> None:
    # Введення-виведення та справжній час — лише тут
    lines = Path(DATA).read_text(encoding="utf-8").splitlines()
    real_now = time.time()

    # ---------------- Завдання 1 ----------------
    title("Завдання 1. Чисті функції")
    res1 = process(lines, now=1000.0)
    res2 = process(lines, now=1000.0)
    print(f"  рядків: {len(lines)}, записів: {len(res1.records)}, помилок: {res1.errors}")
    check("res1 == res2 (детермінованість)", res1 == res2)
    check("res.errors == кількості некоректних рядків (5)", res1.errors == 5)
    check("received_at == 1000.0 у кожного запису", all(r["received_at"] == 1000.0 for r in res1.records))
    res3 = process(lines[:3], now=real_now)
    check("стан не накопичується між викликами", len(res3.records) == 3 and res3.errors == 0)

    normalized = [normalize(r) for r in res1.records]

    # ---------------- Завдання 2 ----------------
    title("Завдання 2. Незмінний запис")
    rec = to_record(normalized[0])
    print("  ", rec)
    try:
        rec.value = 999  # type: ignore[misc]
        check("FrozenInstanceError", False)
    except FrozenInstanceError:
        check("присвоєння полю -> FrozenInstanceError", True)
    rec2 = add_to_value(rec, 10)
    rec3 = with_device(rec, "sensor-99")
    check("add_to_value повертає новий запис", rec2.value == rec.value + 10 and rec2 is not rec)
    check("вхідний запис не змінився", rec.value == 12 and rec.device == "sensor-01")
    check("with_device повертає новий запис", rec3.device == "sensor-99" and rec.device == "sensor-01")
    check("Record у set", len({rec, rec, rec2}) == 2)
    check("Record ключем dict", {rec: "x"}[rec] == "x")
    try:
        {MutableRecord("a", "b", 1, "t", 0.0)}
        check("MutableRecord нехешований", False)
    except TypeError as e:
        check(f"звичайний @dataclass у set -> TypeError ({e})", True)

    # ---------------- Завдання 3 ----------------
    title("Завдання 3. HOF та замикання")
    f, g, h = (lambda x: x + 1), (lambda x: x * 2), (lambda x: x - 3)
    print(f"  compose(f,g,h)(10) = {compose(f, g, h)(10)}  (f(g(h(x))))")
    print(f"  pipe(f,g,h)(10)    = {pipe(f, g, h)(10)}  (h(g(f(x))))")
    check("compose == f(g(h(x)))", compose(f, g, h)(10) == f(g(h(10))))
    check("pipe == h(g(f(x)))", pipe(f, g, h)(10) == h(g(f(10))))
    check("порядок різний", compose(f, g, h)(10) != pipe(f, g, h)(10))
    raw = res1.records[0]
    check("pipe(...) == normalize", normalize_piped(raw) == normalize(raw))
    check("цикл по списку функцій == normalize", normalize_loop(raw) == normalize(raw))

    ge5 = make_predicate("value", "ge", 5)
    is_temp = make_predicate("metric", "eq", "temp")
    check("ge: value >= 5", ge5({"value": 5}) and not ge5({"value": 4}))
    check("eq: metric == 'temp'", is_temp({"metric": "temp"}) and not is_temp({"metric": "humidity"}))

    rt1, rt2 = make_running_total(), make_running_total()
    rt1(5), rt1(10), rt2(1)
    check("незалежні лічильники (15 та 1)", rt1(0) == 15 and rt2(0) == 1)

    kept = [r for r in normalized if keep(r)]
    agg = run_mfr(lines)
    rt = make_running_total()
    last = 0
    for r in kept:
        last = rt(r["value"])
    print(f"  агрегація: {agg}")
    check("running_total == сума значень агрегації", last == sum(agg.values()))
    print("  classify:", {v: classify(v) for v in (3, 12, 50)})

    # ---------------- Завдання 4 ----------------
    title("Завдання 4. map/filter/reduce та включення")
    a, b = run_mfr(lines), run_comp(lines)
    print("  A:", a)
    print("  B:", b)
    check("A == B", a == b)
    check("порожній вхід -> {} (обидва способи)", run_mfr([]) == {} and run_comp([]) == {})

    # ---------------- Завдання 5 ----------------
    title("Завдання 5. partial та каррирування")
    print(f"  to_km(100) = {to_km(100)}, to_miles(100) = {to_miles(100)}")
    check("to_km(100) != to_miles(100)", to_km(100) != to_miles(100))
    add3 = lambda x, y, z: x + y * z
    check("curry3(f)(1)(2)(3) == f(1,2,3)", curry3(add3)(1)(2)(3) == add3(1, 2, 3))
    check("curry3(f)(1) — функція", callable(curry3(add3)(1)))
    p_partial = keep_ge_value(5)
    p_curry = curried_predicate("value")("ge")(5)
    check("partial-предикат == keep", run_mfr(lines, p_partial) == a)
    check("каррирований предикат == keep", run_mfr(lines, p_curry) == a)

    # ---------------- Завдання 6 ----------------
    title("Завдання 6. Генератори")
    with open(DATA, encoding="utf-8") as fh:  # файл читається рядок за рядком
        lazy_res = run_lazy(fh)
    check("генераторний конвеєр == конвеєр із завдання 4", lazy_res == a)
    with open(DATA, encoding="utf-8") as fh:
        lazy_kept = list(g_keep(g_normalize(g_parse(fh))))
    parsed_ok = [p for p in map(parse, lines) if p is not None]
    expected = [r for r in map(normalize, parsed_ok) if keep(r)]
    check("той самий відфільтрований набір записів", lazy_kept == expected)

    five = list(islice(record_stream(), 5))
    check("islice(record_stream(), 5) — 5 записів", len(five) == 5)
    piped = list(islice(g_keep(g_normalize(record_stream())), 5))
    print("  перші 5 відібраних із нескінченного потоку:")
    for r in piped:
        print("    ", r)
    print("  take(3, range(10)) =", list(take(3, range(10))))
    print("  drop(3, range(6))  =", list(drop(3, range(6))))
    gen = g_parse(lines)
    first_pass = list(gen)
    second_pass = list(gen)
    check(f"вичерпаний генератор: 1-й обхід {len(first_pass)}, 2-й {len(second_pass)}", second_pass == [])

    # рекурсія по дереву
    tree = Node("factory", 0, (
        Node("hall-A", 0, (Node("sensor-01", 12), Node("sensor-02", 7))),
        Node("hall-B", 0, (Node("sensor-03", 5),)),
    ))
    print(f"  дерево: total = {total(tree)}, depth = {depth(tree)}")
    check("total(tree) == 24", total(tree) == 24)

    # ---------------- Калькулятор ----------------
    title("Калькулятор виразів (match/case)")
    cases = [
        ("Num(0)", Num(0)),
        ("Num(7)", Num(7)),
        ("Neg(Num(3))", Neg(Num(3))),
        ("(2 + 3) * 4", Mul(Add(Num(2), Num(3)), Num(4))),
        ("10 - 4", Sub(Num(10), Num(4))),
        ("Div(9, 3)", Div(Num(9), Num(3))),
        ("Div(1, 0)", Div(Num(1), Num(0))),
        ("Pow(2, 10)", Pow(Num(2), 10)),
        ("Pow(2, -1)", Pow(Num(2), -1)),
        ("Sqrt(16)", Sqrt(Num(16))),
        ("Sqrt(-4)", Sqrt(Num(-4))),
        ("Sum(1, 2, 3)", Sum((Num(1), Num(2), Num(3)))),
        ("Sum()", Sum(())),
        ("вкладений", Add(Sqrt(Sum((Num(9), Num(7)))), Neg(Pow(Num(2), 3)))),
        ("невідомий вузол", "abc"),
    ]
    for label, expr in cases:
        try:
            print(f"  {label:18} = {evaluate(expr)}")  # type: ignore[arg-type]
        except ValueError as e:
            print(f"  {label:18} -> ValueError: {e}")


if __name__ == "__main__":
    main()
