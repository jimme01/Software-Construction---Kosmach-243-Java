package task2;

/** Спільний контракт для ValidatorService і CachedValidationService. */
public interface EntityValidator {
    ValidationResult validate(Object entity);
}
