package geometry;

public final class Rectangle implements Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = Guard.positiveFinite(width, "width");
        this.height = Guard.positiveFinite(height, "height");
    }

    public Rectangle withWidth(double newWidth) { return new Rectangle(newWidth, height); }
    public Rectangle withHeight(double newHeight) { return new Rectangle(width, newHeight); }

    @Override
    public double area() { return width * height; }
}