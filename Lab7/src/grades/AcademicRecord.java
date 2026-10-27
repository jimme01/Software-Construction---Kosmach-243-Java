package grades;

import java.util.List;

public record AcademicRecord(String studentId, String studentName, List<CourseGrade> grades, String curatorEmail) {
}
