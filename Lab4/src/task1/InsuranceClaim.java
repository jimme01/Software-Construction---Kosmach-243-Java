package task1;

import java.time.LocalDate;

/** Закрита ієрархія (sum type): заява може бути лише в одному з трьох станів. */
public sealed interface InsuranceClaim
        permits InsuranceClaim.Submitted, InsuranceClaim.Approved, InsuranceClaim.Rejected {

    /** Подана заява. */
    record Submitted(LocalDate claimDate, double declaredAmount) implements InsuranceClaim {
    }

    /** Схвалена заява. */
    record Approved(double payoutAmount, boolean isFullCoverage) implements InsuranceClaim {
    }

    /** Відхилена заява. */
    record Rejected(String rejectionReason, boolean appealAllowed) implements InsuranceClaim {
    }
}
