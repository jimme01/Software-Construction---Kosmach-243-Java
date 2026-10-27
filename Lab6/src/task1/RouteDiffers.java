package task1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Анотація рівня класу: значення двох полів (first, second) не повинні збігатися. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RouteDiffers {
    String first();

    String second();

    String message() default "значення полів не повинні збігатися";
}
