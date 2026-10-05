package task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Варіант 4: L3 = слова з L1, яких немає в L2, в оберненому порядку до L1.
 * Результат — у файл, по 5 слів у рядку.
 */
public class Main {
    private static final int PER_LINE = 5;

    public static void main(String[] args) throws IOException {
        List<String> l1 = readWords(Path.of("data/L1.txt"));
        List<String> l2 = readWords(Path.of("data/L2.txt"));
        System.out.println("L1: " + l1);
        System.out.println("L2: " + l2);

        List<String> l3 = new ArrayList<>();
        for (int i = l1.size() - 1; i >= 0; i--) {
            String word = l1.get(i);
            if (!l2.contains(word)) {
                l3.add(word);
            }
        }
        System.out.println("L3: " + l3);

        List<String> lines = new ArrayList<>();
        for (int i = 0; i < l3.size(); i += PER_LINE) {
            lines.add(String.join(" ", l3.subList(i, Math.min(i + PER_LINE, l3.size()))));
        }
        Files.write(Path.of("data/L3.txt"), lines);
        System.out.println("Записано у data/L3.txt:");
        lines.forEach(System.out::println);
    }

    private static List<String> readWords(Path file) throws IOException {
        List<String> words = new ArrayList<>();
        for (String line : Files.readAllLines(file)) {
            if (!line.isBlank()) {
                words.addAll(Arrays.asList(line.trim().split("\\s+")));
            }
        }
        return words;
    }
}
