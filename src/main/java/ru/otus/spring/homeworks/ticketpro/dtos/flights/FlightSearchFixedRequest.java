package ru.otus.spring.homeworks.ticketpro.dtos.flights;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FlightSearchFixedRequest {

    private LocalDate flightDate;
    private String departureAirportCode;
    private String arrivalAirportCode;

}
