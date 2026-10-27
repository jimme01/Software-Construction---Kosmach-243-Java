package grades;

import java.math.BigDecimal;

public record ProcessingResult(double gpa, BigDecimal scholarship, String transcript) {
}
