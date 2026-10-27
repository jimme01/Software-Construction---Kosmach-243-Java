package payments;

/** Тестова підміна сповіщувача: лише рахує відправлені чеки. */
public class RecordingPaymentNotifier implements PaymentNotifier {

    private int sent = 0;

    @Override
    public void sendReceipt(String payerContact, String transactionId, double amount) {
        sent++;
    }

    public int getSentCount() { return sent; }
}
