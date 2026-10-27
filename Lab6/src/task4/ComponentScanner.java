package task4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

/** Сканує пакет (каталог з .class-файлами) і повертає класи з анотацією @Component. */
public class ComponentScanner {

    public static List<Class<?>> findComponents(String packageName)
            throws IOException, URISyntaxException, ClassNotFoundException {
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);

        List<Class<?>> result = new ArrayList<>();
        while (resources.hasMoreElements()) {
            Path directory = Path.of(resources.nextElement().toURI());
            try (Stream<Path> files = Files.list(directory)) {
                List<Path> classFiles = files
                        .filter(p -> p.getFileName().toString().endsWith(".class"))
                        .sorted(Comparator.comparing(Path::toString))
                        .toList();
                for (Path file : classFiles) {
                    String fileName = file.getFileName().toString();
                    String className = packageName + "." + fileName.substring(0, fileName.length() - 6);
                    Class<?> type = Class.forName(className);
                    if (type.isAnnotationPresent(Component.class)) {
                        result.add(type);
                    }
                }
            }
        }
        return result;
    }
}
