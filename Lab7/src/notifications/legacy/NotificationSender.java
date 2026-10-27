package notifications.legacy;

// Опис предметної області: Мультиканальна система інформування користувачів.
// Завдання коду: Форматування та надсилання повідомлень через вибраний транспортний канал.

public class NotificationSender {

    public boolean send(NotificationMessage message, NotificationChannel channel) {
        switch (channel) {
            case EMAIL:
                System.out.println("[EMAIL] To: " + message.getRecipient());
                System.out.println("[EMAIL] Subject: " + message.getSubject());
                System.out.println("[EMAIL] Body: " + message.getBody());
                System.out.println("[EMAIL] Статус: Відправлено через SMTP сервер.");
                return true;

            case SMS:
                // Для SMS обрізаємо довжину тексту до 160 символів
                String body = message.getBody();
                String shortText = body.length() > 160 ? body.substring(0, 157) + "..." : body;
                System.out.println("[SMS] Phone: " + message.getRecipient());
                System.out.println("[SMS] Text: " + shortText);
                System.out.println("[SMS] Статус: Надіслано через GSM шлюз.");
                return true;

            case PUSH:
                System.out.println("[PUSH] DeviceToken: " + message.getRecipient());
                System.out.println("[PUSH] Payload: { \"title\": \"" + message.getSubject() + "\", \"body\": \"" + message.getBody() + "\" }");
                System.out.println("[PUSH] Статус: Доставлено через Firebase Cloud Messaging.");
                return true;

            case TELEGRAM:
                System.out.println("[TELEGRAM] ChatId: " + message.getRecipient());
                System.out.println("[TELEGRAM] Message: *" + message.getSubject() + "*\n" + message.getBody());
                System.out.println("[TELEGRAM] Статус: Надіслано через Bot API.");
                return true;

            default:
                throw new UnsupportedOperationException("Канал сповіщення " + channel + " не підтримується.");
        }
    }
}
