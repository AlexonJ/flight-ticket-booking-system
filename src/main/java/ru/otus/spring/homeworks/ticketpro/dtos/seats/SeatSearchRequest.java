package ru.otus.spring.homeworks.ticketpro.dtos.seats;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.otus.spring.homeworks.ticketpro.models.db.seats.enums.SeatLocation;
import ru.otus.spring.homeworks.ticketpro.validators.ValidSeatPositionCharacter;

import java.time.LocalDate;

@Data
public class SeatSearchRequest {

    @NotBlank(message = "Flight number cannot be empty")
    private String flightNumber;

    @NotNull(message = "Flight date cannot be empty")
    private LocalDate flightDate;

    private SeatLocation seatLocation;

    private Long seatRow;

    @ValidSeatPositionCharacter
    private Character seatPosition;

    private Boolean isFree;

}