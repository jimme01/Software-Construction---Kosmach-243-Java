package payments;

/** Production-реалізація шлюзу (низькорівнева деталь). */
public class StripePaymentGateway implements PaymentGateway {

    @Override
    public boolean charge(String cardNumber, double amount) {
        System.out.println("[Stripe API] Виконано списання " + amount + " UAH з картки " + cardNumber + ".");
        return true;
    }
}
