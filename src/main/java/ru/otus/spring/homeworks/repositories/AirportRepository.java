package ru.otus.spring.homeworks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homeworks.models.db.airports.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {

}
