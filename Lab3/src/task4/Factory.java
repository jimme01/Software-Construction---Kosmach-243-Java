package task4;

public class Factory extends Organization {
    private static final double TAX_RATE = 0.18;

    private final String productType;
    private final int monthlyOutput;
    private final int shiftsPerDay;

    public Factory(String name, int foundedYear, int employeesCount, double annualRevenue,
                   String productType, int monthlyOutput, int shiftsPerDay) {
        super(name, foundedYear, employeesCount, annualRevenue, ActivityType.MANUFACTURING);
        this.productType = productType;
        this.monthlyOutput = monthlyOutput;
        this.shiftsPerDay = shiftsPerDay;
    }

    @Override
    public double calculateAnnualTax() {
        return getAnnualRevenue() * TAX_RATE;
    }

    @Override
    public String getSpecificInfo() {
        return String.format("Продукція: %s, випуск на місяць: %d, змін на добу: %d, "
                        + "річний випуск: %d, на одного працівника: %.1f",
                productType, monthlyOutput, shiftsPerDay,
                calculateAnnualOutput(), calculateOutputPerEmployee());
    }

    // ----- власні методи -----

    public long calculateAnnualOutput() {
        return 12L * monthlyOutput;
    }

    public double calculateOutputPerEmployee() {
        return (double) calculateAnnualOutput() / getEmployeesCount();
    }
}
