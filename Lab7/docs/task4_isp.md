# Завдання 4. ISP: багатофункціональний пристрій (варіант 3)

Вихідний інтерфейс: `printing/legacy/IMultiFunctionDevice` — п'ять методів (друк, сканування, факс, степлер, двосторонній друк).

## 1. Проблема

Обидві реалізації змушені підтримувати весь інтерфейс:

- `BasicLaserPrinter` — 3 методи-заглушки (`scanDocument`, `sendFax`, `staplePages`);
- `DocumentScanner` — 4 методи-заглушки (`printDocument`, `sendFax`, `staplePages`, `duplexPrint`).

Усього 7 методів, що кидають `UnsupportedOperationException`. Клієнт, якому потрібне лише сканування, залежить і від друку, факсу та степлера, і падає, якщо отримає принтер (див. `OfficeClient` у демо).

## 2. Клієнти й ролі

| Клієнт | Що йому потрібно | Роль (інтерфейс) |
|---|---|---|
| `PrintService` | друк | `Printer` |
| `ArchiveService` | сканування | `ScannerDevice` |
| `BookletService` | двосторонній друк і степлер | `DuplexPrinter`, `Stapler` |
| `FaxService` | відправка факсу | `FaxSender` |

## 3. Матриця «пристрій × роль»

| Пристрій | `Printer` | `DuplexPrinter` | `ScannerDevice` | `FaxSender` | `Stapler` |
|---|---|---|---|---|---|
| `BasicLaserPrinter` | так | так | | | |
| `DocumentScanner` | | | так | | |
| `OfficeMfp` (повний МФП) | так | так | так | так | так |

Кожен клас реалізує тільки ті ролі, які справді вміє. Порожніх методів і виключень-заглушок немає.

## 4. Клієнти залежать лише від потрібного контракту

Компілятор тепер не дозволяє передати пристрій без потрібної ролі:

```java
new ArchiveService(new BasicLaserPrinter());
// error: incompatible types: BasicLaserPrinter cannot be converted to ScannerDevice
```

Раніше така помилка виявлялася лише під час виконання.

## 5. Демонстрація

`PrintingDemo` показує помилку у вихідному коді («Basic laser printer has no scanner unit.») і перевіряє, що в новій архітектурі всі клієнти працюють з тими пристроями, які мають потрібні ролі.
