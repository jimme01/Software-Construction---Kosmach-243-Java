package shapes;

/**
 * Guard Clauses для доменної моделі фігур.
 * Єдине джерело правди (SSOT) для правила "розмір — скінченне додатне число" (DRY).
 */
final class Guard {

    private Guard() {}

    static double positiveFinite(double value, String name) {
        if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0) {
            throw new IllegalArgumentException(
                    name + " must be a finite number greater than zero, but was " + value);
        }
        return value;
    }
}
