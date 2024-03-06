package ru.otus.spring.homeworks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homeworks.models.db.airlines.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

}
