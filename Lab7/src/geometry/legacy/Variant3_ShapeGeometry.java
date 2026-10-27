package geometry.legacy;

// LEGACY (вихідний код варіанта 3, завдання 3 — LSP).
public class Variant3_ShapeGeometry {

    public static class Rectangle {
        private double width;
        private double height;

        public Rectangle() {}

        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        public double getWidth() { return width; }
        public void setWidth(double width) { this.width = width; }

        public double getHeight() { return height; }
        public void setHeight(double height) { this.height = height; }

        public double getArea() { return width * height; }
    }

    public static class Square extends Rectangle {
        @Override
        public void setWidth(double width) {
            super.setWidth(width);
            super.setHeight(width);
        }

        @Override
        public void setHeight(double height) {
            super.setWidth(height);
            super.setHeight(height);
        }
    }

    public static class AreaCalculator {
        public void resizeAndVerifyArea(Rectangle rect) {
            rect.setWidth(5);
            rect.setHeight(10);
            double expectedArea = 50;
            double actualArea = rect.getArea();
            if (actualArea != expectedArea) {
                throw new RuntimeException("LSP Violation detected! Expected area " + expectedArea + ", but got " + actualArea);
            }
        }
    }
}