package ru.otus.spring.homeworks.ticketpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.homeworks.ticketpro.models.db.airports.Airport;
import ru.otus.spring.homeworks.ticketpro.models.db.flights.Flight;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//@RepositoryRestResource(path = "flights")
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByNumber(String Number);
    @Query("SELECT f FROM Flight f JOIN ScheduleRecord fs ON fs.flight = f WHERE f.number = :number AND CAST(fs.departureDateTime AS DATE) = :date")
    Optional<Flight> findByNumberAndDate(String number, LocalDate date);

    @Query("SELECT f FROM Flight f\n" +
            "JOIN FETCH f.schedule fs \n" +
            "    WHERE (f.departureAirport = :departureAirport OR :departureAirport IS NULL)\n" +
            "    AND (f.arrivalAirport = :arrivalAirport OR :arrivalAirport IS NULL)\n" +
            "    AND (CAST(fs.departureDateTime AS DATE) = :flightDate OR :flightDate IS NULL) ")
    List<Flight> findAllByDepartureAirportAndArrivalAirportAndFlightDate(Airport departureAirport, Airport arrivalAirport, LocalDate flightDate);
}
