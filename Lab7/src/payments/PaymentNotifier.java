package payments;

/** Абстракція сповіщення платника (SMS, e-mail, push — бізнес-логіці байдуже). */
public interface PaymentNotifier {
    void sendReceipt(String payerContact, String transactionId, double amount);
}
