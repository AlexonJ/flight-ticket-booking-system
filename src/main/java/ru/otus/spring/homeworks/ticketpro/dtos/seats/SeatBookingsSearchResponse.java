package ru.otus.spring.homeworks.ticketpro.dtos.seats;

import lombok.Data;
import ru.otus.spring.homeworks.ticketpro.dtos.bookings.SeatBookingDto;

import java.util.List;

@Data
public class SeatBookingsSearchResponse {


    private List<SeatBookingDto> seatBookings;

}
