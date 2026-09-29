package task3;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US); // дробові числа через крапку

        System.out.println("Трикутник: введіть сторони a, b, c");
        Triangle triangle = new Triangle(sc.nextDouble(), sc.nextDouble(), sc.nextDouble());

        System.out.println("Чотирикутник: введіть сторони a, b, c, d та діагоналі e, f");
        Quadrilateral quad = new Quadrilateral(sc.nextDouble(), sc.nextDouble(), sc.nextDouble(),
                sc.nextDouble(), sc.nextDouble(), sc.nextDouble());

        System.out.println("Прямокутний трикутник: введіть катети a, b та вершину прямого кута");
        RightTriangle right = new RightTriangle(sc.nextDouble(), sc.nextDouble(), sc.next());

        // try-with-resources гарантує виклик close() («деструктора») для кожного об'єкта
        try (triangle; quad; right) {
            // Динамічний поліморфізм: змінні базового типу, виклик перевизначених методів
            Triangle[] figures = {triangle, quad, right};

            System.out.println("\n--- Інформація про об'єкти ---");
            for (Triangle figure : figures) {
                System.out.println(figure.info());
            }

            System.out.print("\nКоефіцієнт масштабування для оновлення об'єктів: ");
            double k = sc.nextDouble();
            for (Triangle figure : figures) {
                figure.scale(k);
            }

            System.out.println("\n--- Після оновлення (розміри × " + k + ") ---");
            for (Triangle figure : figures) {
                System.out.println(figure.info());
            }
            System.out.println();
        }
    }
}
