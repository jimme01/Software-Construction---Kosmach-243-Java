package grades;

import java.util.List;

public class GpaCalculator {
    public double calculate(List<CourseGrade> grades) {
        double weightedScore = 0;
        int credits = 0;
        for (CourseGrade grade : grades) {
            weightedScore += grade.score() * grade.ectsCredits();
            credits += grade.ectsCredits();
        }
        return weightedScore / credits;
    }
}
