package shapes;

/**
 * Незмінний Value Object «квадрат». НЕ успадковує Rectangle:
 * у квадрата одна незалежна величина (сторона), а в прямокутника — дві,
 * тому "квадрат — це прямокутник" не виконується на рівні поведінки.
 * Інваріант: side > 0.
 */
public final class Square implements Shape {

    private final double side;

    public Square(double side) {
        this.side = Guard.positiveFinite(side, "side");
    }

    public double side() { return side; }

    public Square withSide(double newSide) {
        return new Square(newSide);
    }

    @Override
    public double area() {
        return side * side;
    }

    @Override
    public String toString() {
        return "Square[" + side + "]";
    }
}
