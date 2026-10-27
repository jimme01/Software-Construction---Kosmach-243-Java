# Лабораторна робота №7. SOLID, DRY, KISS, YAGNI (варіант 3, Java)

Команда виконує варіант **3** для всіх п'яти завдань.

| Завдання | Принцип | Предметна область | Папка | Виконавець |
|---|---|---|---|---|
| 1 | SRP | Оцінки студента й стипендія | `grades` | Влад |
| 2 | OCP | Мультиканальні сповіщення | `notifications` | Влад |
| 3 | LSP | Геометричні фігури (Rectangle/Square) | `geometry` | напарник |
| 4 | ISP | Багатофункціональний принтер | `printing` | Влад |
| 5 | DIP | Фінансовий процесінг платежів | `payments` | напарник |
| 6 | DRY, KISS, YAGNI | Спільна частина | `docs/task6_dry_kiss_yagni.md` | разом |

## Структура

```
laba7/
├── README.md
├── docs/                 ← аналіз, метрики, відповіді для захисту
└── src/
    ├── Main.java         ← запускає демо всіх завдань
    ├── common/           ← Check, OutputCapture (спільні утиліти демо)
    ├── grades/           ← завдання 1; legacy/ містить вихідний код «до»
    ├── notifications/    ← завдання 2
    └── printing/         ← завдання 4
```

## Запуск (JDK 17 або новіший)

З кореня `laba7`:

```
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out Main
```

Кожен пакет можна запустити окремо: `java -cp out grades.GradesDemo`, `java -cp out notifications.NotificationsDemo`, `java -cp out printing.PrintingDemo`.

Під Windows PowerShell список файлів: `javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName`.

Демо створюють файли `scholarship_registry.csv` та `output/scholarship_registry.csv` (вони вже в `.gitignore`).

## Як додати частину напарника

1. Додати папки `src/geometry` і `src/payments` з класами `GeometryDemo` і `PaymentsDemo` (метод `run()`).
2. Додати їхні виклики в `src/Main.java`.
3. Вихідний код кожного завдання тримати в підпапці `legacy`.

## Зміни у вихідному коді з XML

Вихідні класи перенесено до `legacy` без зміни логіки. Виправлено лише те, що не компілювалося:

- кілька `public`-класів в одному файлі розділено на окремі файли;
- у завданні 2 відновлено екрановані символи `\"` і `\n` (у XML вони загубилися);
- прибрано невикористаний імпорт `RoundingMode`;
- для завдання 4 додано клієнта `OfficeClient`, щоб показати проблему (у вихідному коді клієнта не було).
