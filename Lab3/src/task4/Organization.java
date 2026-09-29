package task4;

import java.time.LocalDate;

/** Абстрактний базовий клас: 2 конкретні та 2 абстрактні методи. */
public abstract class Organization {
    private final String name;
    private final int foundedYear;
    private final int employeesCount;
    private final double annualRevenue;
    private final ActivityType activityType;

    protected Organization(String name, int foundedYear, int employeesCount,
                           double annualRevenue, ActivityType activityType) {
        this.name = name;
        this.foundedYear = foundedYear;
        this.employeesCount = employeesCount;
        this.annualRevenue = annualRevenue;
        this.activityType = activityType;
    }

    public String getName() {
        return name;
    }

    public int getFoundedYear() {
        return foundedYear;
    }

    public int getEmployeesCount() {
        return employeesCount;
    }

    public double getAnnualRevenue() {
        return annualRevenue;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    // ----- конкретні методи -----

    public int getAge(int currentYear) {
        return currentYear - foundedYear;
    }

    public String getFullInfo() {
        return String.format("[%s] %s, засн. %d (%d р.), працівників: %d, дохід: %.2f, податок: %.2f%n    %s",
                activityType.getLabel(), name, foundedYear, getAge(LocalDate.now().getYear()),
                employeesCount, annualRevenue, calculateAnnualTax(), getSpecificInfo());
    }

    // ----- абстрактні методи -----

    public abstract double calculateAnnualTax();

    public abstract String getSpecificInfo();
}
