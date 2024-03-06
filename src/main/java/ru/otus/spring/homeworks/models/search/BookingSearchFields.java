package ru.otus.spring.homeworks.models.search;

import java.time.LocalDate;


public enum BookingSearchFields implements Searchable{

    FLIGHT_NUMBER("flight.number", String.class),
    FLIGHT_DATE("seatBooking.flightDate", LocalDate.class);

    private final String relativePath;

    private final Class fieldClass;

    BookingSearchFields(String path, Class fieldClass) {
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
