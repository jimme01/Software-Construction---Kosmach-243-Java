package task1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Анотація поля: текст повинен відповідати регулярному виразу. */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchesPattern {
    String regex();

    String message() default "значення не відповідає шаблону";
}
