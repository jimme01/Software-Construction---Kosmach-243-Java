package payments.legacy;

// LEGACY (вихідний код варіанта 3, завдання 5 — DIP). Залишено без змін для порівняння.
// Опис предметної області: Еквайринг та фінансовий процесінг онлайн-платежів.
// Завдання коду: Проведення безготівкового платежу через зовнішній платіжний шлюз, фіксація фінансової операції у бухгалтерському реєстрі та SMS-інформування платника.

public class Variant3_FinancialTransactionProcessor {

    public static class PaymentRequest {
        private String transactionId;
        private String cardNumber;
        private double amount;
        private String payerPhone;

        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
        public String getCardNumber() { return cardNumber; }
        public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
        public String getPayerPhone() { return payerPhone; }
        public void setPayerPhone(String payerPhone) { this.payerPhone = payerPhone; }
    }

    public static class StripePaymentGatewayClient {
        public boolean executeCharge(String cardNumber, double amount) {
            System.out.println("[Stripe API] Виконано списання " + amount + " UAH з картки " + cardNumber + ".");
            return true;
        }
    }

    public static class SqlServerTransactionLedger {
        public void recordTransaction(String transactionId, double amount, String status) {
            System.out.println("[SQL Server Ledger] Записано транзакцію " + transactionId + ": сума=" + amount + ", статус=" + status + ".");
        }
    }

    public static class TwilioSmsNotifier {
        public void sendSmsReceipt(String phone, String transactionId, double amount) {
            System.out.println("[Twilio SMS] Повідомлення на " + phone + ": Платіж " + transactionId + " на суму " + amount + " UAH успішно виконано.");
        }
    }

    public static class FinancialTransactionProcessor {
        private StripePaymentGatewayClient stripeClient;
        private SqlServerTransactionLedger ledger;
        private TwilioSmsNotifier smsNotifier;

        public FinancialTransactionProcessor() {
            this.stripeClient = new StripePaymentGatewayClient();
            this.ledger = new SqlServerTransactionLedger();
            this.smsNotifier = new TwilioSmsNotifier();
        }

        public void processPayment(PaymentRequest request) {
            if (request == null || request.getAmount() <= 0) {
                throw new IllegalArgumentException("Сума транзакції повинна бути більшою за нуль.");
            }

            boolean success = this.stripeClient.executeCharge(request.getCardNumber(), request.getAmount());
            String status = success ? "SUCCESS" : "FAILED";

            this.ledger.recordTransaction(request.getTransactionId(), request.getAmount(), status);
            this.smsNotifier.sendSmsReceipt(request.getPayerPhone(), request.getTransactionId(), request.getAmount());
        }
    }
}
