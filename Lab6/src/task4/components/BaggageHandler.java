package task4.components;

import task4.Component;

@Component(name = "baggage", version = "1.2")
public class BaggageHandler {
    private String baggageCount;

    public void execute() {
        System.out.println("[BaggageHandler] обробка багажу, baggageCount = " + baggageCount);
    }

    @Override
    public String toString() {
        return "BaggageHandler{baggageCount=" + baggageCount + "}";
    }
}
