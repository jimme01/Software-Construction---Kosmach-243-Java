package task1;

/** Доменна сутність «Авіарейс» — рівно 5 полів. */
@RouteDiffers(first = "departureCity", second = "destinationCity",
        message = "пункт вильоту та пункт призначення збігаються")
public class Flight {
    @MatchesPattern(regex = "[A-Z]{2}\\d{3,4}",
            message = "код рейсу має формат: 2 великі літери та 3-4 цифри")
    private String flightNumber;

    private String departureCity;

    private String destinationCity;

    @NonNegative(message = "вартість квитка не може бути від'ємною")
    private double ticketPrice;

    @NonNegative(message = "кількість місць не може бути від'ємною")
    private int availableSeats;

    public Flight(String flightNumber, String departureCity, String destinationCity,
                  double ticketPrice, int availableSeats) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.ticketPrice = ticketPrice;
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return "Flight{" + flightNumber + ", " + departureCity + " -> " + destinationCity
                + ", ціна=" + ticketPrice + ", місць=" + availableSeats + "}";
    }
}
