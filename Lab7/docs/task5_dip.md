# Task 5: DIP (Notifications)

**Проблема (Legacy):** Клас `NotificationSender` самостійно створював об'єкт `EmailService` через `new`. Це створювало жорстке зв'язування (Tight Coupling), через що було неможливо підмінити Email на SMS або використати заглушку для тестів.

**Рішення:** Я виділив інтерфейс `NotificationChannel` (абстракцію). Клас `NotificationService` тепер залежить від цього інтерфейсу, а конкретна реалізація (Email, SMS, Mock) передається через конструктор (Constructor Injection). Це дозволяє легко підміняти реалізації в точці збірки (Composition Root).