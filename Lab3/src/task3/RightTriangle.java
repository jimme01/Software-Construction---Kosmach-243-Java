package task3;

/** Прямокутний трикутник: a, b — катети, c — гіпотенуза (обчислюється). */
public class RightTriangle extends Triangle {
    private String rightAngleVertex;
    private double alphaDeg; // кут навпроти катета a
    private double betaDeg;  // кут навпроти катета b

    /** За замовчуванням: катети 3 і 4, прямий кут у вершині C. */
    public RightTriangle() {
        this(3, 4, "C");
    }

    /** Перезавантаження: рівнобедрений прямокутний трикутник. */
    public RightTriangle(double leg) {
        this(leg, leg, "C");
    }

    /** Ініціалізація: два катети та назва вершини прямого кута. */
    public RightTriangle(double legA, double legB, String rightAngleVertex) {
        super(legA, legB, Math.hypot(legA, legB));
        this.rightAngleVertex = rightAngleVertex;
        this.alphaDeg = Math.toDegrees(Math.atan2(legA, legB));
        this.betaDeg = 90 - alphaDeg;
    }

    @Override
    public double perimeter() {
        return getA() + getB() + Math.hypot(getA(), getB());
    }

    @Override
    public double area() {
        return getA() * getB() / 2;
    }

    @Override
    public String info() {
        return String.format("Прямокутний трикутник: катети a=%.2f, b=%.2f, гіпотенуза c=%.2f, "
                        + "прямий кут у вершині %s, кути %.1f° і %.1f°, периметр=%.2f, площа=%.2f",
                getA(), getB(), getC(), rightAngleVertex, alphaDeg, betaDeg, perimeter(), area());
    }
}
