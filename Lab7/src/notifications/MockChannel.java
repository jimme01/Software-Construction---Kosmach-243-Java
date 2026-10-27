package notifications;

public class MockChannel implements NotificationChannel {
    @Override
    public void send(String message) {
        System.out.println("[MOCK] Caught message: " + message);
    }
}