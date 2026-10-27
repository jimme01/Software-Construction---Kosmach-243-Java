package task2;

import java.util.List;

public record ValidationResult(boolean isValid, List<String> errors) {
    public static ValidationResult of(List<String> errors) {
        return new ValidationResult(errors.isEmpty(), List.copyOf(errors));
    }
}
