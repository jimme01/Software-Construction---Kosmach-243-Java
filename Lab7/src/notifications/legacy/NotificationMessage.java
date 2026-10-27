package notifications.legacy;

public class NotificationMessage {
    private String recipient;
    private String subject;
    private String body;
    private String priority;

    public NotificationMessage(String recipient, String subject, String body, String priority) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.priority = priority;
    }

    public String getRecipient() { return recipient; }
    public String getSubject() { return subject; }
    public String getBody() { return body; }
    public String getPriority() { return priority; }
}
