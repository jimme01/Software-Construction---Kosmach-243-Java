package task3;

public class Main {

    /** Аналог AddExtraPointsRef: отримує копію посилання на той самий об'єкт — оригінал змінюється. */
    static void addExtraPoints(ApplicantDossier dossier, int points) {
        dossier.setExtraPoints(dossier.getExtraPoints() + points);
    }

    /** Аналог AddExtraPoints(value): перепризначення параметра не впливає на викликаючий код. */
    static void addExtraPointsLost(ApplicantDossier dossier, int points) {
        dossier = new ApplicantDossier(dossier.getFullName(), dossier.getExamPoints(),
                dossier.getExtraPoints() + points, dossier.getPrivilege());
        System.out.println("  усередині методу: " + dossier);
    }

    /** Для незмінного типу результат треба повернути. */
    static AdmittedStudent addBonus(AdmittedStudent student, int points) {
        return student.withTotalPoints(student.totalPoints() + points);
    }

    public static void main(String[] args) {
        for (PrivilegeFlags p : PrivilegeFlags.values()) {
            System.out.println(p + ": код=" + p.getCode() + ", " + p.getTitle());
        }

        // --- мутабельний клас ---
        ApplicantDossier dossier = new ApplicantDossier("Іваненко Олег", 170, 0, PrivilegeFlags.MEDALIST);
        System.out.println("\nДо:                 " + dossier);

        System.out.println("Перепризначення параметра в методі:");
        addExtraPointsLost(dossier, 10);
        System.out.println("Після (оригінал):   " + dossier);

        addExtraPoints(dossier, 10);
        System.out.println("Зміна через об'єкт: " + dossier);

        // --- незмінний тип ---
        AdmittedStudent student = new AdmittedStudent("Іваненко Олег", 180, 3, PrivilegeFlags.MEDALIST);
        System.out.println("\nДо:                         " + student);

        addBonus(student, 5); // результат загублено — «втрачена мутація»
        System.out.println("Результат не збережено:     " + student);

        student = addBonus(student, 5);
        System.out.println("Результат збережено:        " + student);
        // student.totalPoints = 1;  // помилка компіляції: поля record-а final
    }
}
