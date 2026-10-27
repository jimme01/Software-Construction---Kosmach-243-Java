package geometry;

public class GeometryDemo {
    public static void run() {
        System.out.println("--- Task 3: LSP (Geometry) ---");
        
        Shape rect = new Rectangle(5, 10);
        Shape sq = new Square(5);

        System.out.println("Rectangle area (5x10): " + rect.getArea() + " (correct)");
        System.out.println("Square area (5x5): " + sq.getArea() + " (correct)");
        System.out.println("LSP fixed: Square no longer inherits from Rectangle.");
    }
}