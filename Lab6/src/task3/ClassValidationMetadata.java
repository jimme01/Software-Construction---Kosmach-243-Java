package task3;

import task1.MatchesPattern;
import task1.NonNegative;
import task1.RouteDiffers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/** Підготовлені метадані валідації одного класу (готові Field, Pattern, повідомлення). */
public class ClassValidationMetadata {
    public record RouteRule(Field first, Field second, String message) {
    }

    public record PatternRule(Field field, Pattern pattern, String message) {
    }

    public record NonNegativeRule(Field field, String message) {
    }

    private final RouteRule routeRule;                // може бути null
    private final List<PatternRule> patternRules = new ArrayList<>();
    private final List<NonNegativeRule> nonNegativeRules = new ArrayList<>();

    private ClassValidationMetadata(RouteRule routeRule) {
        this.routeRule = routeRule;
    }

    /** Єдиний «дорогий» розбір типу через рефлексію. */
    public static ClassValidationMetadata parse(Class<?> type) {
        try {
            RouteRule route = null;
            RouteDiffers annotation = type.getAnnotation(RouteDiffers.class);
            if (annotation != null) {
                Field first = type.getDeclaredField(annotation.first());
                Field second = type.getDeclaredField(annotation.second());
                first.setAccessible(true);
                second.setAccessible(true);
                route = new RouteRule(first, second,
                        annotation.first() + " / " + annotation.second() + ": " + annotation.message());
            }

            ClassValidationMetadata metadata = new ClassValidationMetadata(route);
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                MatchesPattern pattern = field.getAnnotation(MatchesPattern.class);
                if (pattern != null) {
                    metadata.patternRules.add(new PatternRule(field,
                            Pattern.compile(pattern.regex()),
                            field.getName() + ": " + pattern.message()));
                }
                NonNegative nonNegative = field.getAnnotation(NonNegative.class);
                if (nonNegative != null) {
                    metadata.nonNegativeRules.add(new NonNegativeRule(field,
                            field.getName() + ": " + nonNegative.message()));
                }
            }
            return metadata;
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Помилка розбору метаданих " + type.getName(), e);
        }
    }

    public RouteRule routeRule() {
        return routeRule;
    }

    public List<PatternRule> patternRules() {
        return patternRules;
    }

    public List<NonNegativeRule> nonNegativeRules() {
        return nonNegativeRules;
    }
}
