package task2;

import task1.Flight;

public class Main {
    public static void main(String[] args) {
        EntityValidator validator = new ValidatorService();

        Flight valid = new Flight("PS101", "Київ", "Лондон", 4200, 15);
        Flight invalid = new Flight("123", "Київ", "Київ", -50, -3);

        print(validator, valid);
        print(validator, invalid);
    }

    private static void print(EntityValidator validator, Flight flight) {
        ValidationResult result = validator.validate(flight);
        System.out.println(flight);
        System.out.println("  IsValid = " + result.isValid());
        for (String error : result.errors()) {
            System.out.println("  - " + error);
        }
        System.out.println();
    }
}
