package ru.otus.spring.homeworks.ticketpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homeworks.ticketpro.models.db.aircraft.Aircraft;
import ru.otus.spring.homeworks.ticketpro.models.db.seats.Seat;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    Optional<Seat> getSeatByAircraftAndRowAndPosition(Aircraft aircraft, Integer rowNumber, Character position);

}
