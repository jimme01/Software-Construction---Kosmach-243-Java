package shapes;

/**
 * Незмінний (immutable) Value Object «прямокутник».
 * Інваріант: width > 0 і height > 0 — перевіряється fail-fast у конструкторі.
 * Змінити розмір можна лише створенням нового об'єкта (withWidth/withHeight),
 * тому "зміна однієї сторони ламає іншу" принципово неможлива.
 */
public final class Rectangle implements Shape {

    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = Guard.positiveFinite(width, "width");
        this.height = Guard.positiveFinite(height, "height");
    }

    public double width() { return width; }
    public double height() { return height; }

    public Rectangle withWidth(double newWidth) {
        return new Rectangle(newWidth, height);
    }

    public Rectangle withHeight(double newHeight) {
        return new Rectangle(width, newHeight);
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public String toString() {
        return "Rectangle[" + width + " x " + height + "]";
    }
}
