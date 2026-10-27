package payments;

/**
 * Composition Root — єдине місце, де вибираються конкретні реалізації й
 * збирається граф об'єктів. Це звичайні фабричні методи (Manual DI):
 * вони повертають готовий об'єкт, а не "шукають залежність за типом",
 * тож це НЕ Service Locator.
 */
public final class PaymentsCompositionRoot {

    private PaymentsCompositionRoot() {}

    public static FinancialTransactionProcessor production() {
        return new FinancialTransactionProcessor(
                new StripePaymentGateway(),
                new SqlServerTransactionLedger(),
                new TwilioSmsNotifier());
    }

    public static FinancialTransactionProcessor withDependencies(PaymentGateway gateway,
                                                                 TransactionLedger ledger,
                                                                 PaymentNotifier notifier) {
        return new FinancialTransactionProcessor(gateway, ledger, notifier);
    }
}
