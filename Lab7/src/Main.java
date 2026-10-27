import common.Check;
import grades.GradesDemo;
import notifications.NotificationsDemo;
import payments.PaymentsDemo;
import printing.PrintingDemo;
import shapes.ShapesDemo;

public class Main {
    public static void main(String[] args) {
        GradesDemo.run();
        System.out.println();
        NotificationsDemo.run();
        System.out.println();
        PrintingDemo.run();
        System.out.println();
        ShapesDemo.run();
        System.out.println();
        PaymentsDemo.run();
        System.out.println();

        if (Check.failures() == 0) {
            System.out.println("Усі перевірки пройдено.");
        } else {
            System.out.println("Невдалих перевірок: " + Check.failures());
            System.exit(1);
        }
    }
}
