package task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Зчитує матрицю з текстового файлу порядково. */
public class MatrixReader implements AutoCloseable {
    private final BufferedReader reader;

    public MatrixReader(Path file) throws IOException {
        this.reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
    }

    public int[][] read() throws IOException {
        List<int[]> rows = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            int[] row = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                row[i] = Integer.parseInt(parts[i]);
            }
            rows.add(row);
        }
        return rows.toArray(new int[0][]);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
