package ru.otus.spring.homeworks.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homeworks.dtos.seats.SeatInfo;
import ru.otus.spring.homeworks.dtos.seats.SeatSearchRequest;
import ru.otus.spring.homeworks.dtos.seats.SeatSearchResponse;
import ru.otus.spring.homeworks.exceptions.CommonValidationException;
import ru.otus.spring.homeworks.models.db.aircraft.Aircraft;
import ru.otus.spring.homeworks.models.db.flights.Flight;
import ru.otus.spring.homeworks.models.db.seats.Seat;
import ru.otus.spring.homeworks.repositories.SeatRepository;

import static ru.otus.spring.homeworks.exceptions.ErrorMessages.SEAT_NOT_FOUND_BY_ROW_POSITION;

@RequiredArgsConstructor
@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    private final FlightService flightService;

    private final EntityManager entityManager;

    @Override
    public Seat getSeatByAircraftRowPositionChecked(Aircraft aircraft, Integer seatRow, Character seatPosition) {
        return seatRepository.getSeatByAircraftAndRowAndPosition(aircraft, seatRow, seatPosition).orElseThrow(() ->
                new CommonValidationException(
                        SEAT_NOT_FOUND_BY_ROW_POSITION.getCode(),
                        SEAT_NOT_FOUND_BY_ROW_POSITION.getMessage()
                                .formatted(aircraft.getModel(), aircraft.getId(), seatRow, seatPosition)));
    }

    @Override
    public SeatSearchResponse findSeats(SeatSearchRequest request) {
        Flight flight = flightService.getFlightByNumberAndDateChecked(request.getFlightNumber(), request.getFlightDate());
        String jpaQuery =
                """
                        SELECT NEW ru.otus.spring.homeworks.dtos.seats.SeatInfo (
                            seat.location,
                            CAST(seat.row AS Long),
                            seat.position,
                            CAST(CASE WHEN seatBooking.id IS NULL THEN true ELSE false END AS Boolean))\s
                        FROM Seat AS seat\s
                        LEFT JOIN SeatBooking seatBooking ON\s
                            seat = seatBooking.seat\s
                            AND seatBooking.flight = :flight
                            AND seatBooking.flightDate = :flightDate
                        WHERE\s
                            seat.aircraft = :aircraft
                            AND (seat.location = :seatLocation OR :seatLocation IS NULL) \s
                            AND (seat.row = :seatRow OR :seatRow IS NULL)
                            AND (seat.position = :seatPosition OR :seatPosition IS NULL)
                            AND ((CASE WHEN seatBooking.id IS NULL THEN true ELSE false END) = :isFree OR :isFree IS NULL)\s""";

        TypedQuery<SeatInfo> query = entityManager.createQuery(jpaQuery, SeatInfo.class);

        query.setParameter("flight", flight);
        query.setParameter("flightDate", request.getFlightDate());
        query.setParameter("aircraft", flight.getAircraft());
        query.setParameter("seatLocation", request.getSeatLocation());
        query.setParameter("seatRow", request.getSeatRow());
        query.setParameter("seatPosition", request.getSeatPosition());
        query.setParameter("isFree", request.getIsFree());

        var queryResult = query.getResultList();

        return SeatSearchResponse.builder().flightNumber(flight.getNumber())
                .flightDate(request.getFlightDate()).seats(queryResult).build();

    }
}
