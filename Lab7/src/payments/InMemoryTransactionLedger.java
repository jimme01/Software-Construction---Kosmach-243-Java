package payments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Тестова підміна реєстру: зберігає записи в пам'яті замість SQL Server. */
public class InMemoryTransactionLedger implements TransactionLedger {

    private final List<String> entries = new ArrayList<>();

    @Override
    public void record(String transactionId, double amount, TransactionStatus status) {
        entries.add(transactionId + ":" + amount + ":" + status);
    }

    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}
