package ru.otus.spring.homeworks.ticketpro.models.search;

import java.time.LocalDateTime;


public enum FlightSearchFields implements Searchable{

    NUMBER("flight.number", String.class),
    AIRLINE("airline.id", String.class),
    AIRLINE_NAME("airline.name", String.class),
    DEPARTURE_AIRPORT("flight.departureAirport.id", String.class),
    DEPARTURE_AIRPORT_CODE("flight.departureAirport.code", String.class),
    ARRIVAL_AIRPORT_CODE("flight.arrivalAirport.code", String.class),
    DEPARTURE_TIME("flightSchedule.departureDateTime", LocalDateTime.class),
    ARRIVAL_TIME("flightSchedule.arrivalDateTime", LocalDateTime.class);

    private final String relativePath;

    private final Class fieldClass;

    FlightSearchFields(String path, Class fieldClass) {
        this.relativePath = path;
        this.fieldClass = fieldClass;
    }

    @Override
    public String getPath() {
        return this.relativePath;
    }

    @Override
    public Class getFieldClass() {
        return this.fieldClass;
    }
}
