package ru.otus.spring.homeworks.ticketpro.dtos.flights;

import lombok.Data;
import ru.otus.spring.homeworks.ticketpro.dtos.AircraftDto;
import ru.otus.spring.homeworks.ticketpro.dtos.AirlineDto;
import ru.otus.spring.homeworks.ticketpro.dtos.AirportDto;
import ru.otus.spring.homeworks.ticketpro.dtos.schedule.ScheduleRecordDto;

import java.util.List;

@Data
public class FlightWithScheduleDto {

    private long id;

    private String number;

    private AirlineDto airline;

    private AircraftDto aircraft;

    private AirportDto departureAirport;

    private AirportDto arrivalAirport;

    private List<ScheduleRecordDto> schedule;

}
