package task4;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Дата виробництва (рррр-мм-дд): ");
        LocalDate date = LocalDate.parse(sc.next());
        System.out.print("Час виробництва (гг:хх): ");
        LocalTime time = LocalTime.parse(sc.next());
        System.out.print("Термін придатності (годин): ");
        long shelfLifeHours = sc.nextLong();

        LocalDateTime produced = LocalDateTime.of(date, time);
        LocalDateTime expires = produced.plusHours(shelfLifeHours);
        LocalDateTime now = LocalDateTime.now();

        System.out.println("Придатний до: " + expires);
        if (now.isAfter(expires)) {
            Duration overdue = Duration.between(expires, now);
            System.out.println("УВАГА! Термін придатності минув " + overdue.toHours() + " год тому.");
        } else {
            Duration left = Duration.between(now, expires);
            System.out.println("До закінчення терміну придатності залишилося "
                    + left.toHours() + " год " + left.toMinutesPart() + " хв.");
        }
    }
}
