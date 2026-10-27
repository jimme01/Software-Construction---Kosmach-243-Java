import common.Check;
import geometry.ShapesDemo;
import notifications.NotificationsDemo;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== LAB 7 (Team Merge) =====");
        
        // Твої завдання
        ShapesDemo.run();
        System.out.println();
        NotificationsDemo.run();
        System.out.println();

        // Місце для завдань Влада (коли він їх додасть):
        // grades.GradesDemo.run();
        // printing.PrintingDemo.run();
        // payments.PaymentsDemo.run();

        if (Check.failures() == 0) {
            System.out.println("Усі перевірки пройдено.");
        } else {
            System.out.println("Невдалих перевірок: " + Check.failures());
            System.exit(1);
        }
    }
}