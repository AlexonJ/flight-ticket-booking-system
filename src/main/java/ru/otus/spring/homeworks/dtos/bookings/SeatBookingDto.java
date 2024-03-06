package ru.otus.spring.homeworks.dtos.bookings;

import lombok.Data;
import ru.otus.spring.homeworks.dtos.PassengerDto;
import ru.otus.spring.homeworks.dtos.seats.SeatDto;
import ru.otus.spring.homeworks.dtos.UserDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SeatBookingDto {

    private long id;

    private String flightNumber;

    private SeatDto seat;

    private LocalDate flightDate;

    private LocalDateTime bookingDate;

    private PassengerDto passenger;

    private UserDto user;

}
