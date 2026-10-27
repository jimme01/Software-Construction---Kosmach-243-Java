package payments;

/** Тестова підміна шлюзу: результат задається, звернення запам'ятовуються. Без мережі. */
public class MockPaymentGateway implements PaymentGateway {

    private final boolean result;
    private int chargeCalls = 0;
    private double lastAmount;

    public MockPaymentGateway(boolean result) {
        this.result = result;
    }

    @Override
    public boolean charge(String cardNumber, double amount) {
        chargeCalls++;
        lastAmount = amount;
        return result;
    }

    public int getChargeCalls() { return chargeCalls; }
    public double getLastAmount() { return lastAmount; }
}
