package task3;

import task2.EntityValidator;
import task2.ValidationResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Валідатор з кешем метаданих: розбір типу виконується лише при першому зверненні. */
public class CachedValidationService implements EntityValidator {
    private final Map<Class<?>, ClassValidationMetadata> cache = new HashMap<>();

    @Override
    public ValidationResult validate(Object entity) {
        ClassValidationMetadata metadata =
                cache.computeIfAbsent(entity.getClass(), ClassValidationMetadata::parse);
        List<String> errors = new ArrayList<>();

        try {
            ClassValidationMetadata.RouteRule route = metadata.routeRule();
            if (route != null && Objects.equals(route.first().get(entity), route.second().get(entity))) {
                errors.add(route.message());
            }
            for (ClassValidationMetadata.PatternRule rule : metadata.patternRules()) {
                Object value = rule.field().get(entity);
                if (!(value instanceof String text && rule.pattern().matcher(text).matches())) {
                    errors.add(rule.message());
                }
            }
            for (ClassValidationMetadata.NonNegativeRule rule : metadata.nonNegativeRules()) {
                Object value = rule.field().get(entity);
                if (!(value instanceof Number number && number.doubleValue() >= 0)) {
                    errors.add(rule.message());
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Помилка рефлексії", e);
        }

        return ValidationResult.of(errors);
    }
}
