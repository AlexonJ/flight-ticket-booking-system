package ru.otus.spring.homeworks.dtos.bookings;

import lombok.Data;
import ru.otus.spring.homeworks.dtos.PassengerDto;
import ru.otus.spring.homeworks.dtos.seats.SeatDto;
import ru.otus.spring.homeworks.dtos.flights.FlightDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SeatBookingDtoWithFlightData {

    private long id;

    private FlightDto flight;

    private LocalDate flightDate;

    private SeatDto seat;

    private LocalDateTime bookingDate;

    private PassengerDto passenger;

}
