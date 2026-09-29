package task3;

/**
 * Чотирикутник ABCD: сторони a=AB, b=BC, c=CD (успадковані), d=DA,
 * діагоналі e=AC, f=BD.
 */
public class Quadrilateral extends Triangle {
    private double d;
    private double e;
    private double f;

    /** За замовчуванням: квадрат зі стороною 1. */
    public Quadrilateral() {
        this(1);
    }

    /** Перезавантаження: квадрат зі стороною side. */
    public Quadrilateral(double side) {
        this(side, side, side, side, side * Math.sqrt(2), side * Math.sqrt(2));
    }

    /** Ініціалізація: чотири сторони та дві діагоналі. */
    public Quadrilateral(double a, double b, double c, double d, double e, double f) {
        super(a, b, c);
        this.d = d;
        this.e = e;
        this.f = f;
    }

    @Override
    public double perimeter() {
        return getA() + getB() + getC() + d;
    }

    /** Площа опуклого чотирикутника через сторони та діагоналі (формула Бретшнайдера). */
    @Override
    public double area() {
        double diff = getB() * getB() + d * d - getA() * getA() - getC() * getC();
        return Math.sqrt(4 * e * e * f * f - diff * diff) / 4;
    }

    @Override
    public void scale(double k) {
        super.scale(k);
        d *= k;
        e *= k;
        f *= k;
    }

    @Override
    public String info() {
        return String.format("Чотирикутник: a=%.2f, b=%.2f, c=%.2f, d=%.2f, діагоналі e=%.2f, f=%.2f, "
                        + "периметр=%.2f, площа=%.2f",
                getA(), getB(), getC(), d, e, f, perimeter(), area());
    }
}
