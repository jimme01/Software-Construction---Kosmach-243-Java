package geometry;

public final class Square implements Shape {
    private final double side;

    public Square(double side) {
        this.side = Guard.positiveFinite(side, "side");
    }

    public Square withSide(double newSide) { return new Square(newSide); }

    @Override
    public double area() { return side * side; }
}