package notifications;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class NotificationSender {
    private final Map<String, NotificationChannel> channels = new LinkedHashMap<>();

    public NotificationSender(NotificationChannel... initialChannels) {
        for (NotificationChannel channel : initialChannels) {
            add(channel);
        }
    }

    public void register(NotificationChannel channel) {
        add(channel);
    }

    private void add(NotificationChannel channel) {
        channels.put(key(channel.name()), channel);
    }

    public boolean send(NotificationMessage message, String channelName) {
        NotificationChannel channel = channels.get(key(channelName));
        if (channel == null) {
            throw new UnsupportedOperationException("Канал сповіщення " + channelName + " не підтримується.");
        }
        return channel.send(message);
    }

    private static String key(String channelName) {
        return Objects.requireNonNull(channelName, "channelName").toUpperCase(Locale.ROOT);
    }
}
