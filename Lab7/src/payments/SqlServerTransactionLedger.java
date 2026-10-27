package payments;

/** Production-реалізація реєстру (низькорівнева деталь). */
public class SqlServerTransactionLedger implements TransactionLedger {

    @Override
    public void record(String transactionId, double amount, TransactionStatus status) {
        System.out.println("[SQL Server Ledger] Записано транзакцію " + transactionId
                + ": сума=" + amount + ", статус=" + status + ".");
    }
}
