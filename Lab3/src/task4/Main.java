package task4;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CompanyRegistryImpl registry = new CompanyRegistryImpl();

        registry.addOrganization(new InsuranceCompany("Оберіг", 1998, 320, 45_000_000, 12_000, 3_500, 0.62));
        registry.addOrganization(new Factory("Металург", 1975, 1200, 120_000_000, "сталеві труби", 8_000, 3));
        registry.addOrganization(new InsuranceCompany("Гарант", 2005, 150, 18_000_000, 5_400, 2_800, 0.78));
        registry.addOrganization(new Factory("Електрон", 2003, 700, 64_000_000, "електродвигуни", 2_500, 2));
        registry.addOrganization(new InsuranceCompany("Довіра", 2012, 90, 9_500_000, 3_100, 2_400, 0.55));
        registry.addOrganization(new Factory("Промзв'язок", 1990, 450, 38_000_000, "кабельна продукція", 12_000, 2));

        System.out.println("=== Уся база ===");
        registry.printAll();

        for (ActivityType type : ActivityType.values()) {
            System.out.println("\n=== Фільтр: " + type.getLabel() + " ===");
            List<Organization> found = registry.filterByActivityType(type);
            for (Organization org : found) {
                System.out.println(org.getFullInfo());
            }
            System.out.println("Знайдено: " + found.size());
        }
    }
}
