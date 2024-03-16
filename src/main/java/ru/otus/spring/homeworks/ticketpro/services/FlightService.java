package ru.otus.spring.homeworks.ticketpro.services;

import jakarta.transaction.Transactional;
import ru.otus.spring.homeworks.ticketpro.dtos.SearchRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.SearchResponse;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightCreationRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightDto;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightSearchFixedRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightSummaryResponse;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightWithScheduleDto;
import ru.otus.spring.homeworks.ticketpro.models.db.flights.Flight;
import ru.otus.spring.homeworks.ticketpro.models.search.FlightSearchFields;

import java.time.LocalDate;

public interface FlightService {

    @Transactional
    SearchResponse<FlightDto> getFlights(SearchRequest<FlightSearchFields> searchRequest);

    SearchResponse<FlightWithScheduleDto> getFlightsWithSchedule(FlightSearchFixedRequest request);

    FlightSummaryResponse getFlightSummary(String flightNumber, LocalDate flightDate);
    FlightDto addFlight(FlightCreationRequest flightCreationRequest);

    Flight getFlightByNumberAndDateChecked(String flightNumber, LocalDate date);

    Flight getFlightByNumberChecked(String flightNumber);
}
