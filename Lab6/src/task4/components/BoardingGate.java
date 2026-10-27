package task4.components;

import task4.Component;

@Component(name = "boarding", version = "2.0")
public class BoardingGate {
    private String gateNumber;

    public void execute() {
        System.out.println("[BoardingGate] посадка на рейс, gateNumber = " + gateNumber);
    }

    @Override
    public String toString() {
        return "BoardingGate{gateNumber=" + gateNumber + "}";
    }
}
