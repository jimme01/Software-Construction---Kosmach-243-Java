package geometry;

final class Guard {
    private Guard() {}

    static double positiveFinite(double value, String name) {
        if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0) {
            throw new IllegalArgumentException(name + " must be > 0, but was " + value);
        }
        return value;
    }
}