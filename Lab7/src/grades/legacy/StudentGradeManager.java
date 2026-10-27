package grades.legacy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Опис предметної області: Деканат університету / Система обліку успішності студентів.
// Завдання коду: Обробка оцінок студента за семестр — валідація даних, розрахунок середнього бала (GPA) та розміру академічної стипендії, підготовка текстового витягу з відомості, експорт даних у CSV-файл та відправка сповіщення куратору групи.

public class StudentGradeManager {
    public void processStudentAcademicRecord(String studentId, String studentName, List<CourseGrade> grades, String curatorEmail) {
        // 1. Перевірка вхідних оцінок
        if (studentId == null || studentId.isBlank() || studentName == null || studentName.isBlank()) {
            throw new IllegalArgumentException("Ідентифікатор та ім'я студента не можуть бути порожніми.");
        }

        if (grades == null || grades.isEmpty()) {
            throw new IllegalStateException("Перелік дисциплін порожній.");
        }

        for (CourseGrade g : grades) {
            if (g.getScore() < 0 || g.getScore() > 100) {
                throw new IllegalArgumentException("Неможлива оцінка " + g.getScore() + " з предмета " + g.getCourseName() + ".");
            }
        }

        // 2. Розрахунок середньозваженого бала та призначеної стипендії
        double totalWeightedScore = 0;
        int totalCredits = 0;
        boolean hasFailedCourses = false;

        for (CourseGrade g : grades) {
            totalWeightedScore += g.getScore() * g.getEctsCredits();
            totalCredits += g.getEctsCredits();
            if (g.getScore() < 60) {
                hasFailedCourses = true;
            }
        }

        double gpa = totalWeightedScore / totalCredits;

        BigDecimal scholarshipAmount = BigDecimal.ZERO;
        if (!hasFailedCourses) {
            if (gpa >= 90.0) {
                scholarshipAmount = new BigDecimal("2910.00"); // Підвищена
            } else if (gpa >= 75.0) {
                scholarshipAmount = new BigDecimal("2000.00"); // Звичайна
            }
        }

        // 3. Формування витягу з академічної відомості
        StringBuilder transcript = new StringBuilder();
        transcript.append("==========================================\n");
        transcript.append("АКАДЕМІЧНА ДОВІДКА: ").append(studentName).append(" (ID: ").append(studentId).append(")\n");
        transcript.append("==========================================\n");
        for (CourseGrade g : grades) {
            transcript.append(String.format("%-30s | %d Кредитів | Оцінка: %d%n", g.getCourseName(), g.getEctsCredits(), g.getScore()));
        }
        transcript.append("------------------------------------------\n");
        transcript.append(String.format("Середній бал (GPA): %.2f%n", gpa));
        transcript.append("Призначена стипендія: ").append(scholarshipAmount).append(" грн\n");

        // 4. Експорт даних у CSV для бухгалтерії
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String csvLine = String.format("%s;%s;%.2f;%s;%s%n", studentId, studentName, gpa, scholarshipAmount, dateStr);
        try (FileOutputStream fos = new FileOutputStream("scholarship_registry.csv", true)) {
            fos.write(csvLine.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Помилка експорту у CSV: " + e.getMessage());
        }

        // 5. Відправка звіту куратору групи
        if (curatorEmail != null && !curatorEmail.isBlank()) {
            try {
                System.out.println("Надсилання пошти куратору " + curatorEmail + " через mail.univ.local");
                System.out.println("Тема: Успішність студента " + studentName);
                System.out.println("Тіло: " + transcript.toString());
            } catch (Exception ex) {
                System.out.println("Помилка надсилання відомості куратору: " + ex.getMessage());
            }
        }
    }
}
