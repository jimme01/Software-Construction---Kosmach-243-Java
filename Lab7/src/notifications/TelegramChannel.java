package notifications;

public class TelegramChannel implements NotificationChannel {
    @Override
    public String name() {
        return "TELEGRAM";
    }

    @Override
    public boolean send(NotificationMessage message) {
        System.out.println("[TELEGRAM] ChatId: " + message.recipient());
        System.out.println("[TELEGRAM] Message: *" + message.subject() + "*\n" + message.body());
        System.out.println("[TELEGRAM] Статус: Надіслано через Bot API.");
        return true;
    }
}
