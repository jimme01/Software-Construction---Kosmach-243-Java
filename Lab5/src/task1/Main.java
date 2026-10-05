package task1;

import model.Flight;
import model.FlightData;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        StorageManager<Flight> storage = new StorageManager<>();
        for (Flight flight : FlightData.flights()) {
            storage.add(flight);
        }

        // Пошук за номером рейсу
        Optional<Flight> found = storage.find(f -> f.flightNumber().equals("PS101"));
        System.out.println("Пошук PS101: " + (found.isPresent() ? found.get() : "не знайдено"));
        Optional<Flight> missing = storage.find(f -> f.flightNumber().equals("XX999"));
        System.out.println("Пошук XX999: " + (missing.isPresent() ? missing.get() : "не знайдено"));

        // Три предикати варіанта 4
        Predicate<Flight> international = Flight::isInternational;
        Predicate<Flight> cheapWithSeats = f -> f.ticketPrice() <= 4500 && f.availableSeats() > 0;
        Predicate<Flight> direction = f -> f.departureCity().equals("Київ")
                || f.destinationCity().equals("Лондон");

        print("1. Міжнародні", storage.filter(international));
        print("2. Ціна <= 4500 і є місця", storage.filter(cheapWithSeats));
        print("3. З Києва або до Лондона", storage.filter(direction));

        // Результат фільтрації доступний лише для читання
        List<Flight> readOnly = storage.filter(international);
        try {
            readOnly.add(found.get());
        } catch (UnsupportedOperationException e) {
            System.out.println("\nСписок лише для читання: UnsupportedOperationException");
        }

        // Видалення
        System.out.println("Видалено PS101: " + storage.remove(found.get()));
        System.out.println("Після видалення PS101 знайдено: "
                + storage.find(f -> f.flightNumber().equals("PS101")).isPresent());
    }

    private static void print(String title, List<Flight> flights) {
        System.out.println("\n" + title + " (" + flights.size() + "):");
        for (Flight f : flights) {
            System.out.println("  " + f.flightNumber() + " " + f.departureCity() + " -> "
                    + f.destinationCity() + ", " + f.ticketPrice() + ", місць: " + f.availableSeats());
        }
    }
}
