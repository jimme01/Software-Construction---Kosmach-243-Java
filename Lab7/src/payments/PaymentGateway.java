package payments;

/** Абстракція платіжного шлюзу. Належить високорівневому модулю (бізнес-логіці), а не Stripe. */
public interface PaymentGateway {
    /** @return true, якщо списання успішне */
    boolean charge(String cardNumber, double amount);
}
