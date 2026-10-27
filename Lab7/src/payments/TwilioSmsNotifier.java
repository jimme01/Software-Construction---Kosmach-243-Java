package payments;

/** Production-реалізація сповіщувача (низькорівнева деталь). */
public class TwilioSmsNotifier implements PaymentNotifier {

    @Override
    public void sendReceipt(String payerContact, String transactionId, double amount) {
        System.out.println("[Twilio SMS] Повідомлення на " + payerContact + ": Платіж "
                + transactionId + " на суму " + amount + " UAH успішно виконано.");
    }
}
