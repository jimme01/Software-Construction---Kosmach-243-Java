package notifications;

import common.Check;

public final class NotificationsDemo {
    public static void run() {
        System.out.println("=== Завдання 5. DIP (Сповіщення) ===");
        
        System.out.println("-- Refactored: Production --");
        NotificationService prod = new NotificationService(new EmailChannel());
        prod.notifyUser("Welcome to production!");

        System.out.println("-- Refactored: Mock test --");
        MockChannel mock = new MockChannel();
        NotificationService test = new NotificationService(mock);
        test.notifyUser("Testing...");
        
        Check.that("Mock канал отримав 1 повідомлення", mock.sentCount == 1);
        Check.that("null-залежність -> NPE", Check.errorOf(() -> new NotificationService(null)).startsWith("NullPointerException"));
        Check.that("Порожнє повідомлення -> Exception", Check.errorOf(() -> prod.notifyUser("")).startsWith("IllegalArgumentException"));
    }
}