package model;

import java.time.LocalDateTime;

public record Flight(String flightNumber, String departureCity, String destinationCity,
                     boolean isInternational, double ticketPrice, int availableSeats,
                     LocalDateTime departureTime, int durationMinutes, String status,
                     int airlineId) {
}
