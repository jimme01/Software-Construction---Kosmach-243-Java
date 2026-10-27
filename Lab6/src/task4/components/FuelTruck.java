package task4.components;

import task4.Component;

@Component(name = "fueling", version = "3.1")
public class FuelTruck {
    private String fuelLevel;

    public void execute() {
        System.out.println("[FuelTruck] заправка літака, fuelLevel = " + fuelLevel);
    }

    @Override
    public String toString() {
        return "FuelTruck{fuelLevel=" + fuelLevel + "}";
    }
}
