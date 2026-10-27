package notifications;

public class NotificationService {
    private final NotificationChannel channel;

    // Constructor Injection (DIP)
    public NotificationService(NotificationChannel channel) {
        this.channel = channel;
    }

    public void notifyUser(String message) {
        channel.send(message);
    }
}