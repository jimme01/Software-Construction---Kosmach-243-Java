package task4.components;

public class CleaningCrew {
    private String cleaningPlan;

    public void execute() {
        System.out.println("[CleaningCrew] прибирання салону, cleaningPlan = " + cleaningPlan);
    }

    @Override
    public String toString() {
        return "CleaningCrew{cleaningPlan=" + cleaningPlan + "}";
    }
}
