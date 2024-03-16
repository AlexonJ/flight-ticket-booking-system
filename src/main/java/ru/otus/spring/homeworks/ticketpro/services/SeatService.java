package ru.otus.spring.homeworks.ticketpro.services;

import ru.otus.spring.homeworks.ticketpro.dtos.seats.SeatSearchRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.seats.SeatSearchResponse;
import ru.otus.spring.homeworks.ticketpro.models.db.aircraft.Aircraft;
import ru.otus.spring.homeworks.ticketpro.models.db.seats.Seat;

public interface SeatService {

    Seat getSeatByAircraftRowPositionChecked(Aircraft aircraft, Integer seatRow, Character seatPosition);

    SeatSearchResponse findSeats(SeatSearchRequest request);

//    Seat getSeatByAircraftIdRowPositionChecked(Long aircraftId, Integer seatRow, Character seatPosition);
}
