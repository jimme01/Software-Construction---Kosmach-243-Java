package task2;

import task1.MatchesPattern;
import task1.NonNegative;
import task1.RouteDiffers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/** Рефлексивний валідатор: при кожному виклику заново досліджує метаструктуру типу. */
public class ValidatorService implements EntityValidator {

    @Override
    public ValidationResult validate(Object entity) {
        List<String> errors = new ArrayList<>();
        Class<?> type = entity.getClass();

        try {
            // 1. Валідація рівня класу
            RouteDiffers route = type.getAnnotation(RouteDiffers.class);
            if (route != null) {
                Field first = type.getDeclaredField(route.first());
                Field second = type.getDeclaredField(route.second());
                first.setAccessible(true);
                second.setAccessible(true);
                if (Objects.equals(first.get(entity), second.get(entity))) {
                    errors.add(route.first() + " / " + route.second() + ": " + route.message());
                }
            }

            // 2. Валідація полів (публічних і приватних)
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(entity);

                MatchesPattern pattern = field.getAnnotation(MatchesPattern.class);
                if (pattern != null
                        && !(value instanceof String text && Pattern.matches(pattern.regex(), text))) {
                    errors.add(field.getName() + ": " + pattern.message());
                }

                NonNegative nonNegative = field.getAnnotation(NonNegative.class);
                if (nonNegative != null
                        && !(value instanceof Number number && number.doubleValue() >= 0)) {
                    errors.add(field.getName() + ": " + nonNegative.message());
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Помилка рефлексії", e);
        }

        return ValidationResult.of(errors);
    }
}
