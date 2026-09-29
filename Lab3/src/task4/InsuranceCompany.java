package task4;

public class InsuranceCompany extends Organization {
    private static final double TAX_RATE = 0.20;

    private final int policiesCount;
    private final double averagePremium;
    private final double payoutRatio; // частка премій, що йде на виплати (0..1)

    public InsuranceCompany(String name, int foundedYear, int employeesCount, double annualRevenue,
                            int policiesCount, double averagePremium, double payoutRatio) {
        super(name, foundedYear, employeesCount, annualRevenue, ActivityType.INSURANCE);
        this.policiesCount = policiesCount;
        this.averagePremium = averagePremium;
        this.payoutRatio = payoutRatio;
    }

    @Override
    public double calculateAnnualTax() {
        return getAnnualRevenue() * TAX_RATE;
    }

    @Override
    public String getSpecificInfo() {
        return String.format("Поліси: %d, середня премія: %.2f, коеф. виплат: %.2f, "
                        + "премійний дохід: %.2f, надійна: %s",
                policiesCount, averagePremium, payoutRatio,
                calculatePremiumIncome(), isReliable() ? "так" : "ні");
    }

    // ----- власні методи -----

    public double calculatePremiumIncome() {
        return policiesCount * averagePremium;
    }

    /** Надійна компанія: виплачує не більше 70% зібраних премій. */
    public boolean isReliable() {
        return payoutRatio <= 0.70;
    }
}
