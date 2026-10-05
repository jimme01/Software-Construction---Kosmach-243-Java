package task1;

import task1.InsuranceClaim.Approved;
import task1.InsuranceClaim.Rejected;
import task1.InsuranceClaim.Submitted;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Submitted
        Submitted submitted = new Submitted(LocalDate.of(2026, 9, 1), 25_000);
        Submitted submittedCopy = new Submitted(submitted.claimDate(), 40_000); // змінена сума
        System.out.println("Оригінал: " + submitted);
        System.out.println("Копія:    " + submittedCopy);

        // Approved
        Approved approved = new Approved(120_000, true);
        Approved approvedCopy = new Approved(approved.payoutAmount(), false); // часткове покриття
        System.out.println("\nОригінал: " + approved);
        System.out.println("Копія:    " + approvedCopy);

        // Rejected
        Rejected rejected = new Rejected("Страховий випадок не покривається полісом", false);
        Rejected rejectedCopy = new Rejected(rejected.rejectionReason(), true); // дозволено апеляцію
        System.out.println("\nОригінал: " + rejected);
        System.out.println("Копія:    " + rejectedCopy);
    }
}
