package notifications;

public record NotificationMessage(String recipient, String subject, String body, String priority) {
}
