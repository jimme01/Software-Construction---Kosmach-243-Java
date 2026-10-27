package task4.components;

public class MaintenanceHangar {
    private String workOrder;

    public void execute() {
        System.out.println("[MaintenanceHangar] технічне обслуговування, workOrder = " + workOrder);
    }

    @Override
    public String toString() {
        return "MaintenanceHangar{workOrder=" + workOrder + "}";
    }
}
