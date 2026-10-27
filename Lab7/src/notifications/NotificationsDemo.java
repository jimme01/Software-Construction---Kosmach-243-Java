package notifications;

public class NotificationsDemo {
    public static void run() {
        System.out.println("--- Task 5: DIP (Notifications) ---");
        
        // Composition Root: ми самі обираємо, яку реалізацію підставити
        NotificationService prodService = new NotificationService(new EmailChannel());
        NotificationService smsService = new NotificationService(new SmsChannel());
        NotificationService testService = new NotificationService(new MockChannel());

        prodService.notifyUser("Welcome to production!");
        smsService.notifyUser("Your code is 1234");
        testService.notifyUser("Testing 1, 2, 3...");
    }
}