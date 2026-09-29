package task4;

import java.util.List;

/** Контракт поведінки реєстру організацій. */
public interface CompanyRegistry {
    void addOrganization(Organization organization);

    List<Organization> filterByActivityType(ActivityType type);
}
