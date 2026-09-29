package task4;

import java.util.ArrayList;
import java.util.List;

/** Клас-сервіс: реалізує інтерфейс і інкапсулює базу організацій. */
public class CompanyRegistryImpl implements CompanyRegistry {
    private final List<Organization> organizations = new ArrayList<>();

    @Override
    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    @Override
    public List<Organization> filterByActivityType(ActivityType type) {
        List<Organization> result = new ArrayList<>();
        for (Organization org : organizations) {
            if (org.getActivityType() == type) {
                result.add(org);
            }
        }
        return result;
    }

    /** Виводить повну інформацію про всю базу. */
    public void printAll() {
        for (Organization org : organizations) {
            System.out.println(org.getFullInfo());
        }
    }
}
