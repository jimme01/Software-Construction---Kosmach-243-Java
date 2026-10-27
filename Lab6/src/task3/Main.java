package task3;

import java.util.Random;
import task1.Flight;
import task2.EntityValidator;
import task2.ValidatorService;

public class Main {
    private static final int ITERATIONS = 500_000;
    private static final int WARMUP = 50_000;
    private static final int DATA_SIZE = 1_000;

    public static void main(String[] args) {
        // Дані генеруються заздалегідь за допомогою Random (генерація не входить у замір)
        Random random = new Random(42);
        String[] cities = {"Київ", "Львів", "Одеса", "Лондон", "Варшава"};
        Flight[] data = new Flight[DATA_SIZE];
        for (int i = 0; i < DATA_SIZE; i++) {
            String number = random.nextInt(4) == 0
                    ? "X" + random.nextInt(1000)                                     // некоректний код
                    : "" + (char) ('A' + random.nextInt(26)) + (char) ('A' + random.nextInt(26))
                    + (100 + random.nextInt(9000));                                   // коректний код
            data[i] = new Flight(number,
                    cities[random.nextInt(cities.length)],
                    cities[random.nextInt(cities.length)],
                    random.nextDouble() * 5100 - 100,
                    random.nextInt(210) - 10);
        }

        EntityValidator plain = new ValidatorService();
        EntityValidator cached = new CachedValidationService();

        // Прогрів JIT-компілятора
        run(plain, data, WARMUP);
        run(cached, data, WARMUP);

        long t1 = System.nanoTime();
        int valid1 = run(plain, data, ITERATIONS);
        long plainNs = System.nanoTime() - t1;

        long t2 = System.nanoTime();
        int valid2 = run(cached, data, ITERATIONS);
        long cachedNs = System.nanoTime() - t2;

        System.out.println("Ітерацій: " + ITERATIONS);
        System.out.printf("%-26s %12s %14s %10s%n", "Підхід", "Час, мс", "нс/виклик", "Валідних");
        System.out.printf("%-26s %12.1f %14.1f %10d%n", "ValidatorService",
                plainNs / 1e6, (double) plainNs / ITERATIONS, valid1);
        System.out.printf("%-26s %12.1f %14.1f %10d%n", "CachedValidationService",
                cachedNs / 1e6, (double) cachedNs / ITERATIONS, valid2);
        System.out.printf("Прискорення: ×%.2f%n", (double) plainNs / cachedNs);
    }

    private static int run(EntityValidator validator, Flight[] data, int iterations) {
        int validCount = 0;
        for (int i = 0; i < iterations; i++) {
            if (validator.validate(data[i % data.length]).isValid()) {
                validCount++;
            }
        }
        return validCount;
    }
}
