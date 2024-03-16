package ru.otus.spring.homeworks.ticketpro.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.spring.homeworks.ticketpro.dtos.AircraftDto;
import ru.otus.spring.homeworks.ticketpro.dtos.AirlineDto;
import ru.otus.spring.homeworks.ticketpro.dtos.AirportDto;
import ru.otus.spring.homeworks.ticketpro.dtos.PassengerDto;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightWithScheduleDto;
import ru.otus.spring.homeworks.ticketpro.dtos.seats.SeatDto;
import ru.otus.spring.homeworks.ticketpro.dtos.UserDto;
import ru.otus.spring.homeworks.ticketpro.dtos.bookings.SeatBookingDto;
import ru.otus.spring.homeworks.ticketpro.dtos.bookings.SeatBookingDtoWithFlightData;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightDto;
import ru.otus.spring.homeworks.ticketpro.dtos.schedule.ScheduleRecordDto;
import ru.otus.spring.homeworks.ticketpro.models.db.Passenger;
import ru.otus.spring.homeworks.ticketpro.models.db.ScheduleRecord;
import ru.otus.spring.homeworks.ticketpro.models.db.aircraft.Aircraft;
import ru.otus.spring.homeworks.ticketpro.models.db.airlines.Airline;
import ru.otus.spring.homeworks.ticketpro.models.db.airports.Airport;
import ru.otus.spring.homeworks.ticketpro.models.db.bookings.SeatBooking;
import ru.otus.spring.homeworks.ticketpro.models.db.flights.Flight;
import ru.otus.spring.homeworks.ticketpro.models.db.seats.Seat;
import ru.otus.spring.homeworks.ticketpro.models.db.users.User;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    AircraftDto aircraftToAircraftDto(Aircraft aircraft);
    AirlineDto airlineToAirlineDto(Airline airline);
    AirportDto airportToAirportDto(Airport airport);
    FlightDto flightToFlightDto(Flight flight);
    FlightWithScheduleDto flightToFlightWithScheduleDto(Flight flight);
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
