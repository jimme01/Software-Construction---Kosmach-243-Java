package task4;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Class<?>> components = ComponentScanner.findComponents("task4.components");
        System.out.println("Знайдено компонентів: " + components.size() + "\n");

        for (Class<?> type : components) {
            Component meta = type.getAnnotation(Component.class);
            System.out.println(type.getSimpleName() + " (name=" + meta.name() + ", version=" + meta.version() + ")");

            // Динамічне створення екземпляра
            Object instance = type.getDeclaredConstructor().newInstance();

            // Динамічне встановлення значення поля
            Field field = type.getDeclaredFields()[0];
            field.setAccessible(true);
            field.set(instance, "налаштовано через Reflection");

            // Динамічний виклик методу дії
            Method execute = type.getMethod("execute");
            execute.invoke(instance);

            // Підсумковий стан
            System.out.println("  " + instance + "\n");
        }
    }
}
