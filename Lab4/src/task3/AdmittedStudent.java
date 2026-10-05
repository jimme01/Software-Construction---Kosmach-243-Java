package task3;

/** Незмінний тип: record гарантує final-поля та відсутність setter-ів. */
public record AdmittedStudent(String fullName, int totalPoints, int groupNumber, PrivilegeFlags privilege) {

    /** «Зміна» повертає НОВИЙ об'єкт, оригінал лишається недоторканим. */
    public AdmittedStudent withTotalPoints(int newTotalPoints) {
        return new AdmittedStudent(fullName, newTotalPoints, groupNumber, privilege);
    }
}
