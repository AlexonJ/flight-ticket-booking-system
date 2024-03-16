package ru.otus.spring.homeworks.ticketpro.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homeworks.ticketpro.dtos.SearchRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.SearchResponse;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightCreationRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightDto;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightSearchFixedRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightSummaryResponse;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightWithScheduleDto;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.SeatSummaryInfo;
import ru.otus.spring.homeworks.ticketpro.exceptions.CommonValidationException;
import ru.otus.spring.homeworks.ticketpro.mappers.DtoMapper;
import ru.otus.spring.homeworks.ticketpro.models.db.aircraft.Aircraft;
import ru.otus.spring.homeworks.ticketpro.models.db.airlines.Airline;
import ru.otus.spring.homeworks.ticketpro.models.db.airports.Airport;
import ru.otus.spring.homeworks.ticketpro.models.db.flights.Flight;
import ru.otus.spring.homeworks.ticketpro.models.search.FlightSearchFields;
import ru.otus.spring.homeworks.ticketpro.repositories.AircraftRepository;
import ru.otus.spring.homeworks.ticketpro.repositories.AirlineRepository;
import ru.otus.spring.homeworks.ticketpro.repositories.AirportRepository;
import ru.otus.spring.homeworks.ticketpro.repositories.FlightRepository;
import ru.otus.spring.homeworks.ticketpro.repositories.ScheduleRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.otus.spring.homeworks.ticketpro.exceptions.ErrorMessages.*;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final EntityManager entityManager;

    private final FlightRepository flightRepository;

    private final AircraftRepository aircraftRepository;

    private final AirportRepository airportRepository;

    private final AirportService airportService;

    private final AirlineRepository airlineRepository;

    private final ScheduleRepository scheduleRepository;

    private final EntitySearchService entitySearchService;

    private final DtoMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public SearchResponse<FlightDto> getFlights(SearchRequest<FlightSearchFields> searchRequest) {

        var flightDtos = entitySearchService.findEntities(searchRequest, Flight.class,
                        " JOIN ScheduleRecord flightSchedule ON flight = flightSchedule.flight ")
                .map(mapper::flightToFlightDto).collect(Collectors.toList());

        SearchResponse<FlightDto> flightSearchResponse = new SearchResponse<>();
        flightSearchResponse.setResult(flightDtos);

        return flightSearchResponse;
    }

    @Transactional
    @Override
    public SearchResponse<FlightWithScheduleDto> getFlightsWithSchedule(FlightSearchFixedRequest request) {
        Airport departureAirport = airportService.findByCodeChecked(request.getDepartureAirportCode());
        Airport arrivalAirport = airportService.findByCodeChecked(request.getArrivalAirportCode());
        List<FlightWithScheduleDto> flightDtos = flightRepository.findAllByDepartureAirportAndArrivalAirportAndFlightDate(
                departureAirport, arrivalAirport, request.getFlightDate()).stream().map(mapper::flightToFlightWithScheduleDto).toList();
        return new SearchResponse<>(flightDtos);
    }

    @Transactional
    @Override
    public FlightSummaryResponse getFlightSummary(String flightNumber, LocalDate flightDate) {
        Flight flight = getFlightByNumberAndDateChecked(flightNumber, flightDate);

        var query = entityManager.createQuery(
                "SELECT NEW ru.otus.spring.homeworks.ticketpro.dtos.flights.SeatSummaryInfo(\n" +
                "    seat.location, \n" +
                "    SUM(1), \n" +
                "    SUM(CASE WHEN sb.id IS NULL THEN 0 ELSE 1 END), \n" +
                "    SUM(CASE WHEN sb.id IS NULL THEN 1 ELSE 0 END)\n" +
                "    ) \n" +
                "FROM Seat seat \n" +
                        "LEFT JOIN SeatBooking sb ON seat = sb.seat \n" +
                        "        AND seat.aircraft = :aircraft \n" +
                        "        AND sb.flight = :flight \n" +
                        "        AND sb.flightDate = :flightDate \n" +
                "GROUP BY seat.location", SeatSummaryInfo.class);

        query.setParameter("aircraft", flight.getAircraft());
        query.setParameter("flight", flight);
        query.setParameter("flightDate", flightDate);

        List<SeatSummaryInfo> seatSummaryInfos = query.getResultList();

        return FlightSummaryResponse.builder()
                .flightNumber(flightNumber)
                .seatsTotalAmount(seatSummaryInfos.stream().mapToLong(SeatSummaryInfo::getSeatsTotalAmount).sum())
                .seatsBusy(seatSummaryInfos.stream().mapToLong(SeatSummaryInfo::getSeatsBusy).sum())
                .seatsFree(seatSummaryInfos.stream().mapToLong(SeatSummaryInfo::getSeatsFree).sum())
                .seats(seatSummaryInfos).build();
    }

    @Override
    public FlightDto addFlight(FlightCreationRequest flightCreationRequest) {

        if (flightRepository.findByNumber(flightCreationRequest.getNumber()).isPresent()) {
            throw new CommonValidationException(
                    FLIGHT_ALREADY_EXIST_BY_NUMBER.getCode(),
                    FLIGHT_ALREADY_EXIST_BY_NUMBER.getMessage().formatted(flightCreationRequest.getNumber()));
        }

        var airlineId = flightCreationRequest.getAirlineId();
        Optional<Airline> airline = airlineRepository.findById(airlineId);
        if (airline.isEmpty()) {
            throw new EntityNotFoundException("Airline with id %d not found".formatted(airlineId));
        }

        var aircraftId = flightCreationRequest.getAircraftId();
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftId);
        if (aircraft.isEmpty()) {
            throw new EntityNotFoundException("Aircraft with id %d not found".formatted(aircraftId));
        }

        var arrivalAirportId = flightCreationRequest.getArrivalAirportId();
        Optional<Airport> arrivalAirport = airportRepository.findById(arrivalAirportId);
        if (arrivalAirport.isEmpty()) {
            throw new EntityNotFoundException("Airport with id %d not found".formatted(arrivalAirportId));
        }

        var departureAirportId = flightCreationRequest.getDepartureAirportId();
        Optional<Airport> departureAirport = airportRepository.findById(departureAirportId);
        if (departureAirport.isEmpty()) {
            throw new EntityNotFoundException("Airport with id %d not found".formatted(departureAirportId));
        }

        Flight flight = new Flight(0, flightCreationRequest.getNumber(),
                airline.get(),
                aircraft.get(),
                departureAirport.get(),
                arrivalAirport.get(),
                new ArrayList<>());

        return Optional.of(flightRepository.save(flight)).map(mapper::flightToFlightDto).get();

    }

    @Override
    public Flight getFlightByNumberAndDateChecked(String flightNumber, LocalDate flightDate) {

        Flight flight = flightRepository.findByNumber(flightNumber).orElseThrow(() ->
                new CommonValidationException(
                        FLIGHT_NOT_FOUND_BY_NUMBER.getCode(),
                        FLIGHT_NOT_FOUND_BY_NUMBER.getMessage().formatted(flightNumber)));

        scheduleRepository.findFirstByFlightAndDepartureDate(flight, flightDate).orElseThrow(
                () -> new CommonValidationException(
                        FLIGHT_NOT_FOUND_AT_DATE.getCode(),
                        FLIGHT_NOT_FOUND_AT_DATE.getMessage().formatted(flightNumber, flightDate)));

        return flight;
    }

    @Override
    public Flight getFlightByNumberChecked(String flightNumber) {
        return flightRepository.findByNumber(flightNumber).orElseThrow(
                () -> new CommonValidationException(
                        FLIGHT_NOT_FOUND_BY_NUMBER.getCode(),
                        FLIGHT_NOT_FOUND_BY_NUMBER.getMessage().formatted(flightNumber)));
    }

}

