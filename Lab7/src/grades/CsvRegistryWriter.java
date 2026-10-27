package grades;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CsvRegistryWriter {
    private final Path file;

    public CsvRegistryWriter(Path file) {
        this.file = file;
    }

    public void write(AcademicRecord record, double gpa, BigDecimal scholarship) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String line = String.format("%s;%s;%.2f;%s;%s%n",
                record.studentId(), record.studentName(), gpa, scholarship, date);
        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            Files.writeString(file, line, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Помилка експорту у CSV: " + e.getMessage());
        }
    }
}
