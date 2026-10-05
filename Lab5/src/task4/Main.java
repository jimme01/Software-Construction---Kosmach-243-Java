package task4;

import model.Flight;
import model.FlightData;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightData.flights();

        // 1. Фільтрація: міжнародні рейси дешевше 5000
        List<Flight> cheapInternational = flights.stream()
                .filter(f -> f.isInternational() && f.ticketPrice() < 5000)
                .toList();
        System.out.println("1. Міжнародні рейси дешевше 5000:");
        cheapInternational.forEach(f -> System.out.println("   " + f.flightNumber() + " " + f.ticketPrice()));

        // 2. Сортування (час вильоту ↑, вартість ↓) і проєкція (номер рейсу)
        List<String> numbers = cheapInternational.stream()
                .sorted(Comparator.comparing(Flight::departureTime)
                        .thenComparing(Flight::ticketPrice, Comparator.reverseOrder()))
                .map(Flight::flightNumber)
                .toList();
        System.out.println("\n2. Номери рейсів після сортування: " + numbers);

        // 3. Пагінація: пропустити 1, взяти наступні 6
        List<String> page = numbers.stream().skip(1).limit(6).toList();
        System.out.println("\n3. Сторінка (skip 1, limit 6): " + page);

        // 4. Пошук одного елемента: перший рейс до Лондона зі статусом «вчасно»
        String londonOnTime = flights.stream()
                .filter(f -> f.destinationCity().equals("Лондон") && f.status().equals("вчасно"))
                .findFirst()
                .map(Flight::flightNumber)
                .orElse("не знайдено");
        System.out.println("\n4. Перший рейс до Лондона вчасно: " + londonOnTime);
        String tokyo = flights.stream()
                .filter(f -> f.destinationCity().equals("Токіо"))
                .findFirst()
                .map(Flight::flightNumber)
                .orElse("не знайдено");
        System.out.println("   Перший рейс до Токіо: " + tokyo);

        // 5. Екстремум: найтриваліший політ
        String longest = flights.stream()
                .max(Comparator.comparingInt(Flight::durationMinutes))
                .map(f -> f.flightNumber() + " (" + f.durationMinutes() + " хв)")
                .orElse("немає рейсів");
        System.out.println("\n5. Найтриваліший рейс: " + longest);
    }
}
