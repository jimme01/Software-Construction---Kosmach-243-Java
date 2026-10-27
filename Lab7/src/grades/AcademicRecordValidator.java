package grades;

public class AcademicRecordValidator {
    public static final int MIN_SCORE = 0;
    public static final int MAX_SCORE = 100;

    public void validate(AcademicRecord record) {
        if (isBlank(record.studentId()) || isBlank(record.studentName())) {
            throw new IllegalArgumentException("Ідентифікатор та ім'я студента не можуть бути порожніми.");
        }
        if (record.grades() == null || record.grades().isEmpty()) {
            throw new IllegalStateException("Перелік дисциплін порожній.");
        }
        for (CourseGrade grade : record.grades()) {
            if (grade.score() < MIN_SCORE || grade.score() > MAX_SCORE) {
                throw new IllegalArgumentException(
                        "Неможлива оцінка " + grade.score() + " з предмета " + grade.courseName() + ".");
            }
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
