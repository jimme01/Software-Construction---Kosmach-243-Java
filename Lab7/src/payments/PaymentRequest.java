package payments;

/** Незмінний запит на платіж. */
public final class PaymentRequest {

    private final String transactionId;
    private final String cardNumber;
    private final double amount;
    private final String payerPhone;

    public PaymentRequest(String transactionId, String cardNumber, double amount, String payerPhone) {
        this.transactionId = transactionId;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.payerPhone = payerPhone;
    }

    public String getTransactionId() { return transactionId; }
    public String getCardNumber() { return cardNumber; }
    public double getAmount() { return amount; }
    public String getPayerPhone() { return payerPhone; }
}
