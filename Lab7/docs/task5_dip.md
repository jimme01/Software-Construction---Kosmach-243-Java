# Завдання 5. Dependency Inversion Principle (DIP) — Варіант 3

**Предметна область:** еквайринг та фінансовий процесінг онлайн-платежів.
**Код:** `src/payments/` (рефакторинг), `src/payments/legacy/` (вихідний код), демо — `PaymentsDemo`.

## 1. Проблема вихідного коду

```java
public FinancialTransactionProcessor() {
    this.stripeClient = new StripePaymentGatewayClient();
    this.ledger       = new SqlServerTransactionLedger();
    this.smsNotifier  = new TwilioSmsNotifier();
}
```

Високорівневий клас бізнес-логіки сам створює три низькорівневі деталі (**Tight Coupling**):

| Залежність | Конкретика, від якої залежав клас | Наслідок |
|---|---|---|
| Платіжний шлюз | `StripePaymentGatewayClient` | неможливо протестувати без реальних списань; не замінити Stripe |
| Реєстр | `SqlServerTransactionLedger` | тест вимагає SQL Server |
| Сповіщення | `TwilioSmsNotifier` | кожен тест надсилає реальні SMS |

Порушено обидві частини DIP: високорівневий модуль залежить від низькорівневих, а абстракцій між ними немає.

## 2. Рефакторинг

### 2.1 Абстракції (належать бізнес-логіці)
| Інтерфейс | Метод | Production-реалізація | Тестова підміна |
|---|---|---|---|
| `PaymentGateway` | `boolean charge(card, amount)` | `StripePaymentGateway` | `MockPaymentGateway` |
| `TransactionLedger` | `void record(id, amount, TransactionStatus)` | `SqlServerTransactionLedger` | `InMemoryTransactionLedger` |
| `PaymentNotifier` | `void sendReceipt(contact, id, amount)` | `TwilioSmsNotifier` | `RecordingPaymentNotifier` |

Інтерфейси названо в термінах бізнесу (`charge`, `record`, `sendReceipt`), а не Stripe/SQL/Twilio — тоді деталі справді можна замінити.

### 2.2 Constructor Injection
`FinancialTransactionProcessor` приймає три абстракції в конструкторі, зберігає їх як `final` і перевіряє `Objects.requireNonNull` (fail-fast). У класі немає жодного `new` інфраструктури й жодного імпорту Stripe/SQL/Twilio.

### 2.3 Composition Root
`PaymentsCompositionRoot` — єдине місце, де вибирають реалізації:
- `production()` → Stripe + SQL Server + Twilio;
- `withDependencies(gateway, ledger, notifier)` → будь-яка комбінація (моки для тестів).

Це звичайні фабричні методи (Manual DI), які повертають **готовий** об'єкт. Жодного реєстру "тип → екземпляр" і жодного `getService(...)` всередині бізнес-логіки, тому **Service Locator не використовується**.

### 2.4 Схема залежностей
```
До:    Processor ──new──► Stripe / SqlServer / Twilio

Після: Processor ──► PaymentGateway ◄── StripePaymentGateway / MockPaymentGateway
                 ──► TransactionLedger ◄── SqlServerTransactionLedger / InMemoryTransactionLedger
                 ──► PaymentNotifier ◄── TwilioSmsNotifier / RecordingPaymentNotifier
       (збирає все PaymentsCompositionRoot)
```
Обидва рівні тепер залежать від абстракцій; стрілки залежностей від деталей спрямовані до інтерфейсів — це і є «інверсія».

## 3. Підтвердження (вивід `PaymentsDemo`)
- Production-збірка друкує ті самі `[Stripe API]`, `[SQL Server Ledger]`, `[Twilio SMS]`, що й legacy.
- Із моками (без мережі та БД): успішний платіж → статус `SUCCESS`, 1 виклик шлюзу, 1 запис у реєстрі, 1 чек; відхилений → `FAILED`, запис у реєстрі, чек не надсилається; `amount = 0` → `IllegalArgumentException`, шлюз не викликається; `null`-залежність → `NullPointerException`.

## 4. Свідомі відмінності від legacy (важливо знати на захисті)
1. **Чек лише при успіху.** У legacy SMS «Платіж … успішно виконано» надсилалась навіть при `FAILED` — це логічна помилка. Тепер чек іде тільки коли `success == true`. Якщо викладач вимагає чистий рефакторинг без зміни поведінки — прибрати умову `if (success)` у `FinancialTransactionProcessor`.
2. **`TransactionStatus` (enum)** замість рядків `"SUCCESS"/"FAILED"` — єдине джерело правди для статусів.
3. **`PaymentRequest` став незмінним** (конструктор замість сеттерів), `processPayment` повертає статус — це спрощує перевірку в тестах.
4. Поза межами завдання, але варто знати: повний номер картки друкується в лог. У реальній системі його треба маскувати (PCI DSS).

## 5. Що казати на захисті
- **Чим DIP відрізняється від DI?** DIP — принцип (залежати від абстракцій), DI — техніка його реалізації (передавати залежності ззовні, тут — через конструктор).
- **Чому інтерфейси лежать поряд із бізнес-логікою, а не зі Stripe?** Власником абстракції має бути високорівневий модуль — він диктує, що йому потрібно.
- **Чому не Service Locator?** Він ховає залежності всередині класу, тож їх не видно в конструкторі, а тести залежать від глобального стану.
- **Чи потрібен IoC-контейнер?** Для трьох залежностей достатньо Manual DI в Composition Root (KISS); контейнер — зайва складність.

## 6. Внесок у Завдання 6 (для `task6_dry_kiss_yagni.md`)
- **DRY:** статус транзакції — єдиний `TransactionStatus` (SSOT); правило «сума > 0» перевіряється в одному місці — у процесорі.
- **KISS:** Manual DI замість IoC-контейнера; метод `processPayment` лінійний, з одним guard clause на початку, без вкладеності.
- **YAGNI:** рівно три інтерфейси — по одному на реальну залежність; немає загального `Repository<T>`, фабрик абстрактних фабрик, методів "на майбутнє" та `NotImplementedException`.
