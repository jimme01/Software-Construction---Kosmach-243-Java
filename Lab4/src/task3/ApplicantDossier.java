package task3;

/** Мутабельний клас (аналог звичайної struct/reference type). */
public class ApplicantDossier {
    private String fullName;
    private int examPoints;
    private int extraPoints;
    private PrivilegeFlags privilege;

    public ApplicantDossier(String fullName, int examPoints, int extraPoints, PrivilegeFlags privilege) {
        this.fullName = fullName;
        this.examPoints = examPoints;
        this.extraPoints = extraPoints;
        this.privilege = privilege;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getExamPoints() {
        return examPoints;
    }

    public void setExamPoints(int examPoints) {
        this.examPoints = examPoints;
    }

    public int getExtraPoints() {
        return extraPoints;
    }

    public void setExtraPoints(int extraPoints) {
        this.extraPoints = extraPoints;
    }

    public PrivilegeFlags getPrivilege() {
        return privilege;
    }

    public void setPrivilege(PrivilegeFlags privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return "ApplicantDossier{" + fullName + ", іспити=" + examPoints
                + ", додаткові=" + extraPoints + ", пільга=" + privilege.getTitle() + "}";
    }
}
