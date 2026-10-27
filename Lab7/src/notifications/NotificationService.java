package notifications;

import java.util.Objects;

public class NotificationService {
    private final NotificationChannel channel;

    public NotificationService(NotificationChannel channel) {
        this.channel = Objects.requireNonNull(channel, "channel");
    }

    public void notifyUser(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        channel.send(message);
    }
}