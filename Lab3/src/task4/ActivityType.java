package task4;

public enum ActivityType {
    INSURANCE("Страхування"),
    MANUFACTURING("Виробництво");

    private final String label;

    ActivityType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
