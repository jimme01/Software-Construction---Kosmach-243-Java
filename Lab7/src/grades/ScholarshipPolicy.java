package grades;

import java.math.BigDecimal;
import java.util.List;

public class ScholarshipPolicy {
    public static final int PASS_SCORE = 60;
    public static final double INCREASED_MIN_GPA = 90.0;
    public static final double REGULAR_MIN_GPA = 75.0;
    public static final BigDecimal INCREASED_AMOUNT = new BigDecimal("2910.00");
    public static final BigDecimal REGULAR_AMOUNT = new BigDecimal("2000.00");

    public BigDecimal calculate(List<CourseGrade> grades, double gpa) {
        if (hasFailedCourses(grades)) {
            return BigDecimal.ZERO;
        }
        if (gpa >= INCREASED_MIN_GPA) {
            return INCREASED_AMOUNT;
        }
        if (gpa >= REGULAR_MIN_GPA) {
            return REGULAR_AMOUNT;
        }
        return BigDecimal.ZERO;
    }

    private boolean hasFailedCourses(List<CourseGrade> grades) {
        for (CourseGrade grade : grades) {
            if (grade.score() < PASS_SCORE) {
                return true;
            }
        }
        return false;
    }
}
