package grades;

import common.Check;
import common.OutputCapture;
import grades.legacy.StudentGradeManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GradesDemo {
    private static final Path LEGACY_CSV = Path.of("scholarship_registry.csv");
    private static final Path NEW_CSV = Path.of("output", "scholarship_registry.csv");
    private static final String CURATOR = "curator@univ.local";
    private static final String[] COURSES = {"Математика", "Програмування", "Бази даних"};

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("=== Завдання 1. SRP: оцінки студента ===");
        StudentGradeManager legacyManager = new StudentGradeManager();
        StudentRecordService service = new StudentRecordService(
                new AcademicRecordValidator(), new GpaCalculator(), new ScholarshipPolicy(),
                new TranscriptFormatter(), new CsvRegistryWriter(NEW_CSV), new CuratorNotifier());

        scenario(legacyManager, service, "Відмінник: підвищена стипендія", "ST-001", "Іваненко Олег",
                new int[][]{{95, 5}, {92, 4}, {90, 6}}, true);
        scenario(legacyManager, service, "Хорошист: звичайна стипендія", "ST-002", "Петренко Марія",
                new int[][]{{80, 5}, {78, 4}, {85, 6}}, false);
        scenario(legacyManager, service, "Є заборгованість: без стипендії", "ST-003", "Коваль Андрій",
                new int[][]{{95, 5}, {55, 4}, {90, 6}}, false);

        invalidScenario(legacyManager, service, "Неможлива оцінка 120", "ST-004", "Мороз Ірина",
                new int[][]{{120, 5}});
        invalidScenario(legacyManager, service, "Порожній список дисциплін", "ST-005", "Лисенко Петро",
                new int[][]{});
    }

    private static void scenario(StudentGradeManager legacyManager, StudentRecordService service, String title,
                                 String id, String name, int[][] data, boolean showOutput) {
        System.out.println("\n" + title);
        String before = OutputCapture.capture(
                () -> legacyManager.processStudentAcademicRecord(id, name, legacyGrades(data), CURATOR));
        ProcessingResult[] holder = new ProcessingResult[1];
        String after = OutputCapture.capture(
                () -> holder[0] = service.process(new AcademicRecord(id, name, newGrades(data), CURATOR)));

        System.out.printf("  GPA = %.2f, стипендія = %s грн%n", holder[0].gpa(), holder[0].scholarship());
        if (showOutput) {
            System.out.print(after);
        }
        Check.that("повідомлення куратору однакове до й після", before.equals(after));
        Check.that("рядок у CSV однаковий до й після", lastLine(LEGACY_CSV).equals(lastLine(NEW_CSV)));
    }

    private static void invalidScenario(StudentGradeManager legacyManager, StudentRecordService service,
                                        String title, String id, String name, int[][] data) {
        System.out.println("\n" + title);
        String before = Check.errorOf(
                () -> legacyManager.processStudentAcademicRecord(id, name, legacyGrades(data), CURATOR));
        String after = Check.errorOf(
                () -> service.process(new AcademicRecord(id, name, newGrades(data), CURATOR)));
        System.out.println("  " + after);
        Check.that("та сама помилка до й після", before.equals(after));
    }

    private static List<grades.legacy.CourseGrade> legacyGrades(int[][] data) {
        List<grades.legacy.CourseGrade> result = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            grades.legacy.CourseGrade grade = new grades.legacy.CourseGrade();
            grade.setCourseName(COURSES[i]);
            grade.setScore(data[i][0]);
            grade.setEctsCredits(data[i][1]);
            result.add(grade);
        }
        return result;
    }

    private static List<CourseGrade> newGrades(int[][] data) {
        List<CourseGrade> result = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            result.add(new CourseGrade(COURSES[i], data[i][0], data[i][1]));
        }
        return result;
    }

    private static String lastLine(Path file) {
        try {
            List<String> lines = Files.readAllLines(file);
            return lines.isEmpty() ? "" : lines.get(lines.size() - 1);
        } catch (IOException e) {
            return "<немає файлу: " + e.getMessage() + ">";
        }
    }
}
