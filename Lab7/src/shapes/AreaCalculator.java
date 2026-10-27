package shapes;

import java.util.List;
import java.util.Objects;

/**
 * Клієнтський код. Працює лише з контрактами, які реально гарантовані типом:
 *  - resizeAndVerifyArea приймає саме Rectangle (а не "щось схоже"), тому
 *    передати Square неможливо — помилка виявляється на етапі компіляції;
 *  - totalArea працює з будь-якою Shape — тут підстановка безпечна.
 */
public class AreaCalculator {

    private static final double EPSILON = 1e-9;

    /** Той самий сценарій, що й у legacy: 5 x 10 => площа 50. */
    public Rectangle resizeAndVerifyArea(Rectangle rect) {
        Objects.requireNonNull(rect, "rect");

        Rectangle resized = rect.withWidth(5).withHeight(10);

        double expectedArea = 50;
        double actualArea = resized.area();
        if (Math.abs(actualArea - expectedArea) > EPSILON) {
            throw new IllegalStateException(
                    "Expected area " + expectedArea + ", but got " + actualArea);
        }
        return resized;
    }

    public double totalArea(List<? extends Shape> shapes) {
        Objects.requireNonNull(shapes, "shapes");
        double sum = 0;
        for (Shape shape : shapes) {
            sum += shape.area();
        }
        return sum;
    }
}
