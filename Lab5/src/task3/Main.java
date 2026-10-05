package task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

/** Варіант 4: Map<String, Integer> — частотний аналіз слів і фільтрація за порогом N. */
public class Main {
    public static void main(String[] args) throws IOException {
        // Зчитування та підрахунок частот (getOrDefault — безпечний доступ)
        String text = Files.readString(Path.of("data/text.txt"));
        Map<String, Integer> freq = new HashMap<>();
        for (String word : text.toLowerCase().split("[^\\p{L}]+")) {
            if (!word.isEmpty()) {
                freq.put(word, freq.getOrDefault(word, 0) + 1);
            }
        }

        // Базові операції: перевірка, додавання, оновлення, вилучення
        System.out.println("Є ключ \"java\": " + freq.containsKey("java"));
        System.out.println("Частота \"python\" (немає): " + freq.getOrDefault("python", 0));
        freq.put("python", 1);                                  // додавання
        freq.put("python", freq.get("python") + 4);             // оновлення
        System.out.println("Після додавання/оновлення \"python\": " + freq.get("python"));
        freq.remove("python");                                  // вилучення
        System.out.println("Після вилучення \"python\" є ключ: " + freq.containsKey("python"));

        // Спеціалізована операція: вилучити ключі зі значенням < N
        System.out.print("\nВведіть N: ");
        int n = new Scanner(System.in).nextInt();
        freq.entrySet().removeIf(entry -> entry.getValue() < n);

        // Виведення: за частотою від найбільшої до найменшої
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(freq.entrySet());
        sorted.sort((a, b) -> {
            int byValue = Integer.compare(b.getValue(), a.getValue());
            return byValue != 0 ? byValue : a.getKey().compareTo(b.getKey());
        });
        System.out.println("Слова, що зустрічаються не менше " + n + " разів:");
        for (Map.Entry<String, Integer> entry : sorted) {
            System.out.println("  " + entry.getKey() + " = " + entry.getValue());
        }
    }
}
