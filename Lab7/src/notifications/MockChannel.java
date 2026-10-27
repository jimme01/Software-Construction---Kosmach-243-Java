package notifications;

public class MockChannel implements NotificationChannel {
    public int sentCount = 0;
    @Override
    public void send(String message) {
        sentCount++;
    }
}