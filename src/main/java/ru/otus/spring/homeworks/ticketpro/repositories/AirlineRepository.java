package ru.otus.spring.homeworks.ticketpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homeworks.ticketpro.models.db.airlines.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

}
