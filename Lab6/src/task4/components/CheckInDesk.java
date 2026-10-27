package task4.components;

import task4.Component;

@Component(name = "check-in", version = "1.0")
public class CheckInDesk {
    private String queueStatus;

    public void execute() {
        System.out.println("[CheckInDesk] реєстрація пасажирів, queueStatus = " + queueStatus);
    }

    @Override
    public String toString() {
        return "CheckInDesk{queueStatus=" + queueStatus + "}";
    }
}
