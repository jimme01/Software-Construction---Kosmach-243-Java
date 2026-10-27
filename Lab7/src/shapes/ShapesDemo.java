package shapes;

import common.Check;

import java.util.Arrays;

/** Демонстрація Завдання 3 (LSP): до і після рефакторингу. Викликається з Main. */
public final class ShapesDemo {

    private ShapesDemo() {
    }

    public static void run() {
        System.out.println("=== Завдання 3. LSP (Rectangle / Square) ===");
        legacyDemo();
        refactoredDemo();
    }

    private static void legacyDemo() {
        System.out.println("-- Legacy: порушення LSP --");
        shapes.legacy.Variant3_ShapeGeometry.AreaCalculator legacyCalc =
                new shapes.legacy.Variant3_ShapeGeometry.AreaCalculator();

        String rectangleResult = Check.errorOf(() ->
                legacyCalc.resizeAndVerifyArea(new shapes.legacy.Variant3_ShapeGeometry.Rectangle()));
        String squareResult = Check.errorOf(() ->
                legacyCalc.resizeAndVerifyArea(new shapes.legacy.Variant3_ShapeGeometry.Square()));

        Check.that("Legacy Rectangle: сценарій 5x10 проходить (" + rectangleResult + ")",
                rectangleResult.equals("без помилки"));
        Check.that("Legacy Square ламає клієнта: " + squareResult,
                squareResult.startsWith("RuntimeException: LSP Violation detected!"));
    }

    private static void refactoredDemo() {
        System.out.println("-- Refactored: LSP дотримано --");
        AreaCalculator calc = new AreaCalculator();

        Rectangle original = new Rectangle(2, 3);
        Rectangle resized = calc.resizeAndVerifyArea(original);
        Check.that("Rectangle після resize має площу 50", resized.area() == 50);
        Check.that("Вихідний Rectangle не змінився (immutable)", original.area() == 6);

        Square square = new Square(4).withSide(7);
        Check.that("Square(7) має площу 49", square.area() == 49);

        double total = calc.totalArea(Arrays.asList(new Rectangle(5, 10), new Square(3)));
        Check.that("totalArea(Rectangle 5x10 + Square 3) = 59", total == 59);

        Check.that("Rectangle(-1, 5) -> " + Check.errorOf(() -> new Rectangle(-1, 5)),
                Check.errorOf(() -> new Rectangle(-1, 5)).startsWith("IllegalArgumentException"));
        Check.that("Rectangle(0, 5) -> IllegalArgumentException",
                Check.errorOf(() -> new Rectangle(0, 5)).startsWith("IllegalArgumentException"));
        Check.that("Square(NaN) -> IllegalArgumentException",
                Check.errorOf(() -> new Square(Double.NaN)).startsWith("IllegalArgumentException"));
        Check.that("rect.withWidth(-3) -> IllegalArgumentException",
                Check.errorOf(() -> new Rectangle(2, 3).withWidth(-3)).startsWith("IllegalArgumentException"));

        // Square більше не Rectangle — рядок нижче не скомпілюється:
        // calc.resizeAndVerifyArea(new Square(5));   // compile error
    }
}
