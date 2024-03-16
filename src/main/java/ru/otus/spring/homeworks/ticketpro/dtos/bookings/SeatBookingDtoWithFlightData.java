package ru.otus.spring.homeworks.ticketpro.dtos.bookings;

import lombok.Data;
import ru.otus.spring.homeworks.ticketpro.dtos.PassengerDto;
import ru.otus.spring.homeworks.ticketpro.dtos.seats.SeatDto;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightDto;

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
