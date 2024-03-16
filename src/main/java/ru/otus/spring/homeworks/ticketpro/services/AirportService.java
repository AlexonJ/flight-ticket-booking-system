package ru.otus.spring.homeworks.ticketpro.services;

import ru.otus.spring.homeworks.ticketpro.models.db.airports.Airport;

import java.util.List;

public interface AirportService {
    List<Airport> findAll();

    Airport findByCodeChecked(String code);
}
