# Завдання 2. OCP: сповіщення (варіант 3)

Вихідний клас: `notifications/legacy/NotificationSender` — метод `send` містить `switch` за enum `NotificationChannel`.

## 1. Точка розширення (Extension Point)

Нові канали зв'язку. У вихідному коді для кожного нового каналу доводиться змінювати **два місця у вже протестованому коді**: enum `NotificationChannel` і `switch` у `NotificationSender.send`.

Нова точка розширення: інтерфейс `NotificationChannel` (`name()` та `send(message)`). Усе, що залежить від конкретного каналу, живе в його класі.

## 2. Рефакторинг (патерн Strategy)

| | До | Після |
|---|---|---|
| Вибір каналу | `switch` за enum | Реєстр `Map<String, NotificationChannel>` у `NotificationSender` |
| Логіка каналу | Усередині `switch` | Окремий клас: `EmailChannel`, `SmsChannel`, `PushChannel`, `TelegramChannel` |
| Новий канал | Змінити enum і `switch` | Додати новий клас і викликати `register(...)` |
| Цикломатична складність `send` | 6 | 2 |
| Невідомий канал | `UnsupportedOperationException` | Те саме виключення з тим самим текстом |

## 3. Демонстрація відкритості до розширення

Канал `ViberChannel` додано окремим файлом. Жоден рядок у `NotificationSender`, `EmailChannel`, `SmsChannel`, `PushChannel`, `TelegramChannel` і `NotificationChannel` не змінено. Для підключення потрібен лише виклик:

```java
sender.register(new ViberChannel());
sender.send(message, "VIBER");
```

## 4. Як додати ще один канал

1. Створити клас, що реалізує `NotificationChannel`.
2. Повернути унікальне ім'я каналу з `name()`.
3. Зареєструвати його в точці збірки (`new NotificationSender(...)` або `register`).

## 5. Збереження поведінки

`NotificationsDemo` для кожного з чотирьох каналів запускає вихідний і новий код з однаковим повідомленням і порівнює весь вивід (SMS обрізається до 160 символів, push-payload і Telegram форматуються як раніше). Усі порівняння збігаються.

`UnsupportedOperationException` у `NotificationSender` — це захист від некоректного вводу (невідомий канал), успадкований із вихідного коду, а не заглушка нереалізованого методу.
