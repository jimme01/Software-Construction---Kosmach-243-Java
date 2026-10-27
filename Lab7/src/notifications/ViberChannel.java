package notifications;

public class ViberChannel implements NotificationChannel {
    @Override
    public String name() {
        return "VIBER";
    }

    @Override
    public boolean send(NotificationMessage message) {
        System.out.println("[VIBER] Phone: " + message.recipient());
        System.out.println("[VIBER] Text: " + message.subject() + ": " + message.body());
        System.out.println("[VIBER] Статус: Надіслано через Viber API.");
        return true;
    }
}
