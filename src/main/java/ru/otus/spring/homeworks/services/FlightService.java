package ru.otus.spring.homeworks.services;

import jakarta.transaction.Transactional;
import ru.otus.spring.homeworks.dtos.SearchRequest;
import ru.otus.spring.homeworks.dtos.SearchResponse;
import ru.otus.spring.homeworks.dtos.flights.FlightCreationRequest;
import ru.otus.spring.homeworks.dtos.flights.FlightDto;
import ru.otus.spring.homeworks.dtos.flights.FlightSummaryResponse;
import ru.otus.spring.homeworks.models.db.flights.Flight;
import ru.otus.spring.homeworks.models.search.FlightSearchFields;

import java.time.LocalDate;

public interface FlightService {

    @Transactional
    SearchResponse<FlightDto> getFlights(SearchRequest<FlightSearchFields> searchRequest);
    FlightSummaryResponse getFlightSummary(String flightNumber, LocalDate flightDate);
    FlightDto addFlight(FlightCreationRequest flightCreationRequest);

    Flight getFlightByNumberAndDateChecked(String flightNumber, LocalDate date);

    Flight getFlightByNumberChecked(String flightNumber);
}
