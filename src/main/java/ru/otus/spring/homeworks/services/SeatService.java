package ru.otus.spring.homeworks.services;

import ru.otus.spring.homeworks.dtos.seats.SeatSearchRequest;
import ru.otus.spring.homeworks.dtos.seats.SeatSearchResponse;
import ru.otus.spring.homeworks.models.db.aircraft.Aircraft;
import ru.otus.spring.homeworks.models.db.seats.Seat;

public interface SeatService {

    Seat getSeatByAircraftRowPositionChecked(Aircraft aircraft, Integer seatRow, Character seatPosition);

    SeatSearchResponse findSeats(SeatSearchRequest request);

//    Seat getSeatByAircraftIdRowPositionChecked(Long aircraftId, Integer seatRow, Character seatPosition);
}
