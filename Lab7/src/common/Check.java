package common;

public final class Check {
    private static int failures;

    private Check() {
    }

    public static void that(String label, boolean condition) {
        System.out.println("  [" + (condition ? "OK" : "FAIL") + "] " + label);
        if (!condition) {
            failures++;
        }
    }

    public static String errorOf(Runnable action) {
        try {
            action.run();
            return "без помилки";
        } catch (RuntimeException e) {
            return e.getClass().getSimpleName() + ": " + e.getMessage();
        }
    }

    public static int failures() {
        return failures;
    }
}
