package task1;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

/**
 * Варіант 4: глядацька зала 10 рядів по 15 місць (1 — зайняте, 0 — вільне).
 * Визначити, чи є вільні місця в обраному користувачем ряді.
 */
public class Main {
    private static final int ROWS = 10;
    private static final int SEATS = 15;
    private static final Path FILE = Path.of("hall.txt");

    public static void main(String[] args) {
        // Генерація матриці випадковими значеннями
        Random random = new Random();
        int[][] hall = new int[ROWS][SEATS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < SEATS; j++) {
                hall[i][j] = random.nextInt(100) < 80 ? 1 : 0; // ~80% місць зайнято
            }
        }

        try {
            // Запис і зчитування — всередині try-with-resources
            try (MatrixWriter writer = new MatrixWriter(FILE)) {
                writer.write(hall);
            }
            int[][] loaded;
            try (MatrixReader reader = new MatrixReader(FILE)) {
                loaded = reader.read();
            }

            // Обробка масиву
            Scanner sc = new Scanner(System.in);
            System.out.print("Оберіть ряд (1.." + ROWS + "): ");
            int row = sc.nextInt();

            boolean hasFree = false;
            for (int seat : loaded[row - 1]) {
                if (seat == 0) {
                    hasFree = true;
                }
            }
            System.out.println(hasFree
                    ? "У ряді " + row + " є вільні місця."
                    : "У ряді " + row + " вільних місць немає.");
        } catch (IOException e) {
            System.err.println("Помилка роботи з файлом: " + e.getMessage());
        }
    }
}
