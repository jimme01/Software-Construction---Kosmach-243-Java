package task5;

import model.Airline;
import model.Flight;
import model.FlightData;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {
    /** DTO для inner join. */
    record FlightInfo(String flightNumber, String direction, String airlineName) {
    }

    /** DTO для group join. */
    record AirlineFlights(String airlineName, List<String> flightNumbers) {
    }

    public static void main(String[] args) {
        List<Flight> flights = FlightData.flights();
        List<Airline> airlines = FlightData.airlines();

        // 1. Групування: кількість рейсів за авіакомпанією
        Map<Integer, Long> countByAirline = flights.stream()
                .collect(Collectors.groupingBy(Flight::airlineId, TreeMap::new, Collectors.counting()));
        System.out.println("1. Кількість рейсів за id авіакомпанії: " + countByAirline);

        // 2. Агрегація в групах: середня вартість квитка
        Map<Integer, Double> avgPrice = flights.stream()
                .collect(Collectors.groupingBy(Flight::airlineId, TreeMap::new,
                        Collectors.averagingDouble(Flight::ticketPrice)));
        System.out.println("\n2. Середня вартість квитка:");
        avgPrice.forEach((id, avg) -> System.out.printf("   авіакомпанія %d: %.2f%n", id, avg));

        // 3. Join: рейси + авіакомпанії за airlineId
        List<FlightInfo> joined = airlines.stream()
                .flatMap(a -> flights.stream()
                        .filter(f -> f.airlineId() == a.id())
                        .map(f -> new FlightInfo(f.flightNumber(),
                                f.departureCity() + " -> " + f.destinationCity(), a.name())))
                .toList();
        System.out.println("\n3. Join (перші 5 із " + joined.size() + "):");
        joined.stream().limit(5).forEach(info -> System.out.println("   " + info));

        // 4. GroupJoin / Left Join: авіакомпанія та список її рейсів
        List<AirlineFlights> groupJoin = airlines.stream()
                .map(a -> new AirlineFlights(a.name(), flights.stream()
                        .filter(f -> f.airlineId() == a.id())
                        .map(Flight::flightNumber)
                        .toList()))
                .toList();
        System.out.println("\n4. GroupJoin:");
        groupJoin.forEach(g -> System.out.println("   " + g.airlineName() + ": " + g.flightNumbers()));

        // 5. Зведена агрегація: загальна тривалість усіх рейсів
        int totalMinutes = flights.stream()
                .reduce(0, (sum, f) -> sum + f.durationMinutes(), Integer::sum);
        System.out.println("\n5. Загальна тривалість усіх рейсів: " + totalMinutes + " хв");
    }
}
