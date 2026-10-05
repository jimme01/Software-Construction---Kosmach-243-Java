package model;

import java.time.LocalDateTime;
import java.util.List;

/** Тестові дані для завдань 1, 4 і 5. */
public final class FlightData {
    private FlightData() {
    }

    private static LocalDateTime at(int hour, int minute) {
        return LocalDateTime.of(2026, 10, 10, hour, minute);
    }

    public static List<Flight> flights() {
        return List.of(
                new Flight("PS101", "Київ", "Лондон", true, 4200, 15, at(6, 30), 230, "вчасно", 1),
                new Flight("PS102", "Київ", "Варшава", true, 2800, 0, at(7, 15), 95, "вчасно", 1),
                new Flight("AB201", "Львів", "Варшава", true, 1900, 20, at(8, 0), 60, "вчасно", 2),
                new Flight("AB202", "Київ", "Париж", true, 4900, 8, at(8, 0), 205, "затримується", 2),
                new Flight("EW301", "Одеса", "Берлін", true, 3900, 12, at(9, 45), 180, "вчасно", 3),
                new Flight("EW302", "Київ", "Стамбул", true, 3500, 25, at(10, 30), 130, "вчасно", 3),
                new Flight("PS103", "Київ", "Відень", true, 3100, 5, at(11, 10), 100, "вчасно", 1),
                new Flight("AB203", "Харків", "Київ", false, 1500, 30, at(12, 0), 70, "вчасно", 2),
                new Flight("EW303", "Львів", "Лондон", true, 4700, 3, at(13, 20), 215, "вчасно", 3),
                new Flight("PS104", "Одеса", "Прага", true, 4400, 10, at(14, 5), 150, "затримується", 1),
                new Flight("AB204", "Київ", "Прага", true, 5200, 6, at(15, 40), 140, "вчасно", 2),
                new Flight("EW304", "Київ", "Лондон", true, 6100, 2, at(16, 25), 235, "вчасно", 3),
                new Flight("PS105", "Дніпро", "Львів", false, 1300, 18, at(17, 0), 75, "вчасно", 1),
                new Flight("AB205", "Львів", "Варшава", true, 2500, 14, at(18, 30), 65, "вчасно", 2),
                new Flight("EW305", "Київ", "Берлін", true, 4300, 9, at(19, 45), 125, "вчасно", 3));
    }

    public static List<Airline> airlines() {
        return List.of(
                new Airline(1, "Ukraine Sky"),
                new Airline(2, "Euro Jet"),
                new Airline(3, "Blue Wings"),
                new Airline(4, "Carpathian Air")); // без рейсів — для демонстрації left join
    }
}
