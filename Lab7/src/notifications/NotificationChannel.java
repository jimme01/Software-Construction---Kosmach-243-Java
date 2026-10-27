package notifications;

public interface NotificationChannel {
    String name();

    boolean send(NotificationMessage message);
}
