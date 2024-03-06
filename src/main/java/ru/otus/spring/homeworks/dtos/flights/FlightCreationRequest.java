package ru.otus.spring.homeworks.dtos.flights;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightCreationRequest {

    private long id;

    @NotEmpty(message = "Flight number cannot be empty")
    private String number;

    @Min(value = 0, message = "Airline id cannot be empty")
    private long airlineId;

    @Min(value = 0, message = "Aircraft id cannot be empty")
    private long aircraftId;

    @Min(value = 0, message = "Departure airport id cannot be empty")
    private long departureAirportId;

    @Min(value = 0, message = "Arrival airport id cannot be empty")
    private long arrivalAirportId;

    @NotNull(message = "Departure date cannot be empty")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date cannot be empty")
    private LocalDateTime arrivalDateTime;

}
