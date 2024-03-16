package ru.otus.spring.homeworks.ticketpro.dtos.bookings;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.otus.spring.homeworks.ticketpro.dtos.PassengerDto;
import ru.otus.spring.homeworks.ticketpro.validators.ValidSeatPositionCharacter;

import java.time.LocalDate;

@Data
public class SeatBookingCreationRequest {

    @NotEmpty(message = "Flight number cannot be empty")
    private String flightNumber;

    @NotNull(message = "Flight date cannot be empty")
    private LocalDate flightDate;

    @Min(value = 1, message = "Seat row cannot be empty")
    private Integer seatRow;

    @ValidSeatPositionCharacter(message = "Invalid seat position")
    @NotNull(message = "Seat position cannot be empty")
    private Character seatPosition;

    @NotNull(message = "Passenger data cannot be empty")
    private PassengerDto passenger;

}
