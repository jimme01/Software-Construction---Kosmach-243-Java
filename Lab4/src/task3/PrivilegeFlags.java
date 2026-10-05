package task3;

/** Enum як повноцінний об'єкт: числовий код і назва, приватний конструктор. */
public enum PrivilegeFlags {
    ORPHAN(1, "Сирота"),
    MEDALIST(2, "Медаліст"),
    OLYMPIAD_WINNER(4, "Переможець олімпіади");

    private final int code;
    private final String title;

    private PrivilegeFlags(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public int getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }
}
