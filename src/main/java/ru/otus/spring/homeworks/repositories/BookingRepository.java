package ru.otus.spring.homeworks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.homeworks.models.db.bookings.SeatBooking;
import ru.otus.spring.homeworks.models.db.flights.Flight;
import ru.otus.spring.homeworks.models.db.seats.Seat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<SeatBooking, Long> {

    Optional<SeatBooking> findFirstByFlightAndFlightDateAndSeat(Flight flight, LocalDate date, Seat seat);

    @Query("SELECT sb FROM SeatBooking sb WHERE sb.flight.number = :flightNumber AND sb.flightDate = :flightDate")
    List<SeatBooking> findSeatBookingsByFlightNumberAndFlightDate(Flight flightNumber, LocalDate flightDate);

//    @Query("SELECT sb FROM SeatBooking sb")
//    List<SeatBooking> findAll();
    @Query("SELECT EXISTS (\n" +
            "    SELECT 1\n" +
            "    FROM SeatBooking sb\n" +
            "    WHERE sb.flight.number = :flightNumber\n" +
            "    AND sb.flightDate = :flightDate\n" +
            ") AS SeatBookingExists")
    Boolean flightHasAnyBookings(String flightNumber, LocalDate flightDate);
}
