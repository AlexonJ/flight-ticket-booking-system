package ru.otus.spring.homeworks.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.spring.homeworks.dtos.AircraftDto;
import ru.otus.spring.homeworks.dtos.AirlineDto;
import ru.otus.spring.homeworks.dtos.AirportDto;
import ru.otus.spring.homeworks.dtos.PassengerDto;
import ru.otus.spring.homeworks.dtos.seats.SeatDto;
import ru.otus.spring.homeworks.dtos.UserDto;
import ru.otus.spring.homeworks.dtos.bookings.SeatBookingDto;
import ru.otus.spring.homeworks.dtos.bookings.SeatBookingDtoWithFlightData;
import ru.otus.spring.homeworks.dtos.flights.FlightDto;
import ru.otus.spring.homeworks.dtos.schedule.ScheduleRecordDto;
import ru.otus.spring.homeworks.models.db.Passenger;
import ru.otus.spring.homeworks.models.db.ScheduleRecord;
import ru.otus.spring.homeworks.models.db.aircraft.Aircraft;
import ru.otus.spring.homeworks.models.db.airlines.Airline;
import ru.otus.spring.homeworks.models.db.airports.Airport;
import ru.otus.spring.homeworks.models.db.bookings.SeatBooking;
import ru.otus.spring.homeworks.models.db.flights.Flight;
import ru.otus.spring.homeworks.models.db.seats.Seat;
import ru.otus.spring.homeworks.models.db.users.User;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    AircraftDto aircraftToAircraftDto(Aircraft aircraft);
    AirlineDto airlineToAirlineDto(Airline airline);
    AirportDto airportToAirportDto(Airport airport);
    FlightDto flightToFlightDto(Flight flight);
    SeatDto seatToSeatDto(Seat seat);
    ScheduleRecordDto scheduleRecordToScheduleRecordDto(ScheduleRecord scheduleRecord);
    ScheduleRecord scheduleRecordDtoToScheduleRecord(ScheduleRecordDto scheduleRecordDto);
    @Mapping(target = "flightNumber", expression = "java(seatBooking.getFlight().getNumber())")
    SeatBookingDto seatBookingToSeatBookingDto(SeatBooking seatBooking);

    SeatBookingDtoWithFlightData seatBookingToSeatBookingDtoWithFlightData(SeatBooking seatBooking);
    Passenger passengerDtoToPassenger(PassengerDto passengerDto);
    PassengerDto passengerToPassengerDto(Passenger passenger);

    UserDto userToUserDto(User user);

}
