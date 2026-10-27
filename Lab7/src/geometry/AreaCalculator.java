package geometry;

import java.util.List;

public class AreaCalculator {
    public Rectangle resizeAndVerifyArea(Rectangle rect) {
        Rectangle resized = rect.withWidth(5).withHeight(10);
        if (Math.abs(resized.area() - 50) > 1e-9) {
            throw new IllegalStateException("Expected 50");
        }
        return resized;
    }

    public double totalArea(List<? extends Shape> shapes) {
        double sum = 0;
        for (Shape s : shapes) sum += s.area();
        return sum;
    }
}