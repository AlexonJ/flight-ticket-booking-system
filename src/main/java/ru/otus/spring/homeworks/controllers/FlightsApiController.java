package ru.otus.spring.homeworks.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.homeworks.dtos.SearchRequest;
import ru.otus.spring.homeworks.dtos.SearchResponse;
import ru.otus.spring.homeworks.dtos.flights.FlightCreationRequest;
import ru.otus.spring.homeworks.dtos.flights.FlightDto;
import ru.otus.spring.homeworks.dtos.flights.FlightSummaryResponse;
import ru.otus.spring.homeworks.models.search.FlightSearchFields;
import ru.otus.spring.homeworks.services.FlightService;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/flights")
public class FlightsApiController {

    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<SearchResponse<FlightDto>> getFlights (@RequestBody @Validated SearchRequest<FlightSearchFields> request) {

        return ResponseEntity.ok(flightService.getFlights(request));

    }

    @GetMapping("/{flightNumber}/{flightDate}")
    public ResponseEntity<FlightSummaryResponse> getFlightSummary (
            @PathVariable(name = "flightNumber") String flightNumber,
            @PathVariable(name = "flightDate") LocalDate flightDate) {
        return ResponseEntity.ok(flightService.getFlightSummary(flightNumber, flightDate));
    }

    @PostMapping
    public ResponseEntity<FlightDto> addFlight(@RequestBody @Validated FlightCreationRequest request) {

        return ResponseEntity.ok(flightService.addFlight(request));

    }

}
