package task4.components;

public class TrafficControl {
    private String runwayStatus;

    public void execute() {
        System.out.println("[TrafficControl] керування рухом, runwayStatus = " + runwayStatus);
    }

    @Override
    public String toString() {
        return "TrafficControl{runwayStatus=" + runwayStatus + "}";
    }
}
