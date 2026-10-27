package common;

import java.util.function.Supplier;

public class Check {
    private static int failCount = 0;

    public static void that(String description, boolean condition) {
        if (condition) {
            System.out.println("  [OK] " + description);
        } else {
            System.out.println("  [FAIL] " + description);
            failCount++;
        }
    }

    public static String errorOf(Runnable runnable) {
        try {
            runnable.run();
            return "без помилки";
        } catch (Exception e) {
            return e.getClass().getSimpleName() + ": " + e.getMessage();
        }
    }

    public static String errorOf(Supplier<?> supplier) {
        try {
            supplier.get();
            return "без помилки";
        } catch (Exception e) {
            return e.getClass().getSimpleName() + ": " + e.getMessage();
        }
    }

    public static int failures() {
        return failCount;
    }
}