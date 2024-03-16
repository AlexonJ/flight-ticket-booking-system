package ru.otus.spring.homeworks.ticketpro.services;

import ru.otus.spring.homeworks.ticketpro.dtos.SearchRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.bookings.SeatBookingCreationRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.bookings.SeatBookingDto;
import ru.otus.spring.homeworks.ticketpro.dtos.bookings.SeatBookingDtoWithFlightData;
import ru.otus.spring.homeworks.ticketpro.models.db.bookings.SeatBooking;
import ru.otus.spring.homeworks.ticketpro.models.db.flights.Flight;
import ru.otus.spring.homeworks.ticketpro.models.db.seats.Seat;
import ru.otus.spring.homeworks.ticketpro.models.search.BookingSearchFields;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    List<SeatBookingDtoWithFlightData> findAllWithFlightData();

    SeatBookingDto createBooking(SeatBookingCreationRequest request);

    List<SeatBookingDto> findBookings(SearchRequest<BookingSearchFields> searchRequest);

    void deleteBooking(String flightNumber, LocalDate flightDate, Integer seatRow, Character seatPosition);

    SeatBooking getSeatBookingChecked(Flight flight, LocalDate flightDate, Seat seat);

    boolean seatIsBookedChecked(Flight flight, Seat seat, LocalDate flightDate);

}
