package payments;

import common.Check;

/** Демонстрація Завдання 5 (DIP): до і після рефакторингу. Викликається з Main. */
public final class PaymentsDemo {

    private PaymentsDemo() {
    }

    public static void run() {
        System.out.println("=== Завдання 5. DIP (обробка платежів) ===");
        legacyDemo();
        productionDemo();
        mockedDemo();
    }

    private static void legacyDemo() {
        System.out.println("-- Legacy: new всередині конструктора --");
        payments.legacy.Variant3_FinancialTransactionProcessor.PaymentRequest legacyRequest =
                new payments.legacy.Variant3_FinancialTransactionProcessor.PaymentRequest();
        legacyRequest.setTransactionId("TX-LEGACY");
        legacyRequest.setCardNumber("4111111111111111");
        legacyRequest.setAmount(100);
        legacyRequest.setPayerPhone("+380501234567");
        new payments.legacy.Variant3_FinancialTransactionProcessor.FinancialTransactionProcessor()
                .processPayment(legacyRequest);
        System.out.println("  (підмінити Stripe/SQL/Twilio у legacy неможливо без зміни коду класу)");
    }

    private static void productionDemo() {
        System.out.println("-- Refactored: Production-збірка (Composition Root) --");
        PaymentRequest request = new PaymentRequest("TX-1001", "4111111111111111", 250.0, "+380501234567");
        TransactionStatus status = PaymentsCompositionRoot.production().processPayment(request);
        Check.that("Production-збірка: статус SUCCESS", status == TransactionStatus.SUCCESS);
    }

    private static void mockedDemo() {
        System.out.println("-- Refactored: підміна реалізацій на моки --");
        PaymentRequest request = new PaymentRequest("TX-2001", "4111111111111111", 250.0, "+380501234567");

        // Успішний платіж
        MockPaymentGateway okGateway = new MockPaymentGateway(true);
        InMemoryTransactionLedger ledger = new InMemoryTransactionLedger();
        RecordingPaymentNotifier notifier = new RecordingPaymentNotifier();
        TransactionStatus status = PaymentsCompositionRoot
                .withDependencies(okGateway, ledger, notifier).processPayment(request);

        Check.that("Успішний платіж: статус SUCCESS", status == TransactionStatus.SUCCESS);
        Check.that("Шлюз викликано 1 раз із сумою 250",
                okGateway.getChargeCalls() == 1 && okGateway.getLastAmount() == 250.0);
        Check.that("У реєстрі 1 запис зі статусом SUCCESS",
                ledger.getEntries().size() == 1 && ledger.getEntries().get(0).endsWith("SUCCESS"));
        Check.that("Чек надіслано 1 раз", notifier.getSentCount() == 1);

        // Відхилений платіж
        MockPaymentGateway failGateway = new MockPaymentGateway(false);
        InMemoryTransactionLedger ledger2 = new InMemoryTransactionLedger();
        RecordingPaymentNotifier notifier2 = new RecordingPaymentNotifier();
        TransactionStatus status2 = PaymentsCompositionRoot
                .withDependencies(failGateway, ledger2, notifier2).processPayment(request);

        Check.that("Відхилений платіж: статус FAILED", status2 == TransactionStatus.FAILED);
        Check.that("Відхилений платіж записано в реєстр як FAILED",
                ledger2.getEntries().size() == 1 && ledger2.getEntries().get(0).endsWith("FAILED"));
        Check.that("Чек про успіх НЕ надсилається при FAILED", notifier2.getSentCount() == 0);

        // Валідація та fail-fast
        MockPaymentGateway gw3 = new MockPaymentGateway(true);
        FinancialTransactionProcessor processor3 = PaymentsCompositionRoot
                .withDependencies(gw3, new InMemoryTransactionLedger(), new RecordingPaymentNotifier());
        String zeroAmountError = Check.errorOf(() ->
                processor3.processPayment(new PaymentRequest("TX-0", "4111111111111111", 0, "+380501234567")));
        Check.that("amount=0 -> " + zeroAmountError, zeroAmountError.startsWith("IllegalArgumentException"));
        Check.that("Шлюз не викликався при невалідній сумі", gw3.getChargeCalls() == 0);

        String nullDependencyError = Check.errorOf(() ->
                new FinancialTransactionProcessor(null, ledger, notifier));
        Check.that("null-залежність -> " + nullDependencyError,
                nullDependencyError.startsWith("NullPointerException"));
    }
}
