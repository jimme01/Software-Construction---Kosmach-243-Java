package task1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Відповідає лише за запис готової матриці у текстовий файл. */
public class MatrixWriter implements AutoCloseable {
    private final BufferedWriter writer;

    public MatrixWriter(Path file) throws IOException {
        this.writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8);
    }

    public void write(int[][] matrix) throws IOException {
        for (int[] row : matrix) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < row.length; j++) {
                if (j > 0) {
                    line.append(' ');
                }
                line.append(row[j]);
            }
            writer.write(line.toString());
            writer.newLine();
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
