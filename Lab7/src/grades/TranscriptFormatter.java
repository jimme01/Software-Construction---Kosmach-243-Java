package grades;

import java.math.BigDecimal;

public class TranscriptFormatter {
    private static final String HEAVY_LINE = "==========================================\n";
    private static final String LIGHT_LINE = "------------------------------------------\n";

    public String format(AcademicRecord record, double gpa, BigDecimal scholarship) {
        StringBuilder transcript = new StringBuilder();
        transcript.append(HEAVY_LINE);
        transcript.append("АКАДЕМІЧНА ДОВІДКА: ").append(record.studentName())
                .append(" (ID: ").append(record.studentId()).append(")\n");
        transcript.append(HEAVY_LINE);
        for (CourseGrade grade : record.grades()) {
            transcript.append(String.format("%-30s | %d Кредитів | Оцінка: %d%n",
                    grade.courseName(), grade.ectsCredits(), grade.score()));
        }
        transcript.append(LIGHT_LINE);
        transcript.append(String.format("Середній бал (GPA): %.2f%n", gpa));
        transcript.append("Призначена стипендія: ").append(scholarship).append(" грн\n");
        return transcript.toString();
    }
}
