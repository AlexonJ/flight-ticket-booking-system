package ru.otus.spring.homeworks.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.homeworks.models.db.airports.Airport;
import ru.otus.spring.homeworks.services.AirportService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/airports")
public class AirportsApiController {

    private final AirportService airportService;

    @GetMapping
    ResponseEntity<List<Airport>> getAllAirports() {
        return ResponseEntity.ok(airportService.findAll());
    }

}
