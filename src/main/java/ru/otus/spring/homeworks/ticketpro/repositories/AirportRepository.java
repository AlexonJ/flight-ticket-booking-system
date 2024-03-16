package ru.otus.spring.homeworks.ticketpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homeworks.ticketpro.models.db.airports.Airport;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    Optional<Airport> findByCode(String code);
}
