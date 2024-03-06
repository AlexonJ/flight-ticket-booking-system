package ru.otus.spring.homeworks.services;

import ru.otus.spring.homeworks.models.db.airports.Airport;

import java.util.List;

public interface AirportService {
    List<Airport> findAll();
}
