package ru.otus.spring.homeworks.dtos.seats;

import lombok.Data;
import ru.otus.spring.homeworks.dtos.bookings.SeatBookingDto;

import java.util.List;

@Data
public class SeatBookingsSearchResponse {


    private List<SeatBookingDto> seatBookings;

}
