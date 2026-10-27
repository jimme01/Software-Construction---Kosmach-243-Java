package geometry;

import common.Check;
import java.util.Arrays;

public final class ShapesDemo {
    public static void run() {
        System.out.println("=== Завдання 3. LSP (Rectangle / Square) ===");
        
        System.out.println("-- Legacy --");
        geometry.legacy.Variant3_ShapeGeometry.AreaCalculator calcLegacy = new geometry.legacy.Variant3_ShapeGeometry.AreaCalculator();
        String rectRes = Check.errorOf(() -> calcLegacy.resizeAndVerifyArea(new geometry.legacy.Variant3_ShapeGeometry.Rectangle()));
        String sqRes = Check.errorOf(() -> calcLegacy.resizeAndVerifyArea(new geometry.legacy.Variant3_ShapeGeometry.Square()));
        
        Check.that("Legacy Rectangle: OK", rectRes.equals("без помилки"));
        Check.that("Legacy Square ламає клієнта", sqRes.startsWith("RuntimeException"));

        System.out.println("-- Refactored --");
        AreaCalculator calc = new AreaCalculator();
        Rectangle r = new Rectangle(2, 3);
        Rectangle resized = calc.resizeAndVerifyArea(r);
        Check.that("Rectangle area == 50", resized.area() == 50);
        
        Square s = new Square(4).withSide(7);
        Check.that("Square(7) area == 49", s.area() == 49);
        
        Check.that("totalArea == 59", calc.totalArea(Arrays.asList(new Rectangle(5, 10), new Square(3))) == 59);
        Check.that("Rectangle(-1, 5) -> Exception", Check.errorOf(() -> new Rectangle(-1, 5)).startsWith("IllegalArgumentException"));
    }
}