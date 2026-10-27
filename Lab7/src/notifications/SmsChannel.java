package notifications;

public class SmsChannel implements NotificationChannel {
    private static final int MAX_LENGTH = 160;
    private static final String ELLIPSIS = "...";

    @Override
    public String name() {
        return "SMS";
    }

    @Override
    public boolean send(NotificationMessage message) {
        String body = message.body();
        String shortText = body.length() > MAX_LENGTH
                ? body.substring(0, MAX_LENGTH - ELLIPSIS.length()) + ELLIPSIS
                : body;
        System.out.println("[SMS] Phone: " + message.recipient());
        System.out.println("[SMS] Text: " + shortText);
        System.out.println("[SMS] Статус: Надіслано через GSM шлюз.");
        return true;
    }
}
