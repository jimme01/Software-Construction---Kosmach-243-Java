package payments;

/** Абстракція бухгалтерського реєстру транзакцій. */
public interface TransactionLedger {
    void record(String transactionId, double amount, TransactionStatus status);
}
