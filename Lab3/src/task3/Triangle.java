package task3;

/**
 * Базовий клас: трикутник зі сторонами a, b, c.
 * Java не має деструкторів (finalize() застарів), тому «деструктор» реалізовано
 * через AutoCloseable.close(), який викликається в try-with-resources.
 */
public class Triangle implements AutoCloseable {
    public static final String STUDENT_NAME = "Космач В. Є.";

    private double a;
    private double b;
    private double c;

    /** Конструктор за замовчуванням: трикутник 3-4-5. */
    public Triangle() {
        this(3, 4, 5);
    }

    /** Конструктор перезавантаження: рівносторонній трикутник. */
    public Triangle(double side) {
        this(side, side, side);
    }

    /** Конструктор ініціалізації. */
    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double perimeter() {
        return a + b + c;
    }

    /** Формула Герона. */
    public double area() {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    /** Метод оновлення: пропорційна зміна розмірів. */
    public void scale(double k) {
        a *= k;
        b *= k;
        c *= k;
    }

    /** «Інформація». */
    public String info() {
        return String.format("Трикутник: a=%.2f, b=%.2f, c=%.2f, периметр=%.2f, площа=%.2f",
                a, b, c, perimeter(), area());
    }

    @Override
    public void close() {
        System.out.println("Лабораторна робота виконана студентом 2 курсу " + STUDENT_NAME);
    }
}
