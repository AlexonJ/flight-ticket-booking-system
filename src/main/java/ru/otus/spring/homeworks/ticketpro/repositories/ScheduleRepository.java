package ru.otus.spring.homeworks.ticketpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homeworks.ticketpro.models.db.ScheduleRecord;
import ru.otus.spring.homeworks.ticketpro.models.db.flights.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<ScheduleRecord, Long> {

    @Query("SELECT sr FROM ScheduleRecord sr WHERE sr.flight = :flight AND CAST(sr.departureDateTime AS DATE) = :flightDate" )
    Optional<ScheduleRecord> findFirstByFlightAndDepartureDate(Flight flight, LocalDate flightDate);

    @Query("SELECT sr FROM ScheduleRecord sr WHERE sr.flight.number = :flightNumber")
    List<ScheduleRecord> findAllByFlightNumber(String flightNumber);

    @Transactional
    List<ScheduleRecord> deleteAllByFlightAndDepartureDateTimeIn(Flight flight, List<LocalDateTime> departureDateTime);
}
