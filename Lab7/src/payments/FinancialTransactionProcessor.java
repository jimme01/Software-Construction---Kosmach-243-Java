package payments;

import java.util.Objects;

/**
 * Високорівнева бізнес-логіка платежу.
 * Залежить ВИКЛЮЧНО від абстракцій (PaymentGateway, TransactionLedger, PaymentNotifier),
 * які отримує через конструктор (Constructor Injection). Жодного `new` інфраструктури.
 */
public class FinancialTransactionProcessor {

    private final PaymentGateway gateway;
    private final TransactionLedger ledger;
    private final PaymentNotifier notifier;

    public FinancialTransactionProcessor(PaymentGateway gateway,
                                         TransactionLedger ledger,
                                         PaymentNotifier notifier) {
        this.gateway = Objects.requireNonNull(gateway, "gateway");
        this.ledger = Objects.requireNonNull(ledger, "ledger");
        this.notifier = Objects.requireNonNull(notifier, "notifier");
    }

    public TransactionStatus processPayment(PaymentRequest request) {
        if (request == null || request.getAmount() <= 0) {
            throw new IllegalArgumentException("Сума транзакції повинна бути більшою за нуль.");
        }

        boolean success = gateway.charge(request.getCardNumber(), request.getAmount());
        TransactionStatus status = success ? TransactionStatus.SUCCESS : TransactionStatus.FAILED;

        ledger.record(request.getTransactionId(), request.getAmount(), status);

        // Зміна відносно legacy: чек "платіж успішно виконано" надсилається лише при успіху.
        if (success) {
            notifier.sendReceipt(request.getPayerPhone(), request.getTransactionId(), request.getAmount());
        }
        return status;
    }
}
