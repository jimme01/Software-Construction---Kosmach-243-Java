package notifications;

public class EmailChannel implements NotificationChannel {
    @Override
    public String name() {
        return "EMAIL";
    }

    @Override
    public boolean send(NotificationMessage message) {
        System.out.println("[EMAIL] To: " + message.recipient());
        System.out.println("[EMAIL] Subject: " + message.subject());
        System.out.println("[EMAIL] Body: " + message.body());
        System.out.println("[EMAIL] Статус: Відправлено через SMTP сервер.");
        return true;
    }
}
