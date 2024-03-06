package ru.otus.spring.homeworks.dtos.flights;

import lombok.Data;
import ru.otus.spring.homeworks.dtos.AircraftDto;
import ru.otus.spring.homeworks.dtos.AirlineDto;
import ru.otus.spring.homeworks.dtos.AirportDto;

@Data
public class FlightDto {

    private long id;

    private String number;

    private AirlineDto airline;

    private AircraftDto aircraft;

    private AirportDto departureAirport;

    private AirportDto arrivalAirport;

//    private LocalDateTime departureDateTime;
//
//    private LocalDateTime arrivalDateTime;
//
//    private List<FlightScheduleDto> schedule;
}
