package ru.otus.spring.homeworks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.homeworks.models.db.flights.Flight;

import java.time.LocalDate;
import java.util.Optional;

//@RepositoryRestResource(path = "flights")
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByNumber(String Number);
    @Query("SELECT f FROM Flight f JOIN ScheduleRecord fs ON fs.flight = f WHERE f.number = :number AND CAST(fs.departureDateTime AS DATE) = :date")
    Optional<Flight> findByNumberAndDate(String number, LocalDate date);
}
