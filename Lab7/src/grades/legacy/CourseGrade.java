package grades.legacy;

public class CourseGrade {
    private String courseName;
    private int score; // 0 - 100
    private int ectsCredits;

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getEctsCredits() { return ectsCredits; }
    public void setEctsCredits(int ectsCredits) { this.ectsCredits = ectsCredits; }
}
