package task2;

import task1.InsuranceClaim;
import task1.InsuranceClaim.Approved;
import task1.InsuranceClaim.Rejected;
import task1.InsuranceClaim.Submitted;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        InsuranceClaim[] claims = {
                new Submitted(LocalDate.now(), 750_000),            // велика сума
                new Submitted(LocalDate.of(2020, 1, 15), 30_000),   // прострочена
                new Submitted(LocalDate.now(), 30_000),             // звичайна
                new Approved(250_000, true),                        // обов'язкова умова варіанта
                new Approved(80_000, false),                        // часткове покриття
                new Approved(80_000, true),                         // звичайна виплата
                new Rejected("Шахрайство", false),                  // безпека
                new Rejected("Прострочений поліс", true),           // апеляція
                new Rejected("Прострочений поліс", false),          // остаточна відмова
        };

        for (InsuranceClaim claim : claims) {
            System.out.println(claim);
            System.out.println("  -> " + ClaimRouter.route(claim));
        }
    }
}
