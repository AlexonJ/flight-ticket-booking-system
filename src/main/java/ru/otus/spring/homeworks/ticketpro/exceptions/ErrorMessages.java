package ru.otus.spring.homeworks.ticketpro.exceptions;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    UNABLE_TO_READ_INPUT_MESSAGE(101, "An error occurred while read message: "),
    ERROR_VALIDATING_REQUEST(102, "An error occurred while validating request:"),

    FLIGHT_NOT_FOUND_BY_NUMBER(201, "Flight with number %s does not exist"),
    FLIGHT_ALREADY_EXIST_BY_NUMBER(202, "Flight with number %s already exist"),
    FLIGHT_NOT_FOUND_AT_DATE(203, "There is no flight %s on date %s"),
    FLIGHT_HAS_BOOKINGS(206, "Flight %s on date %s has bookings."),

    SCHEDULE_NOT_FOUND_FOR_FLIGHT_BY_DATE(207, "Flight %s has no date %s in schedule."),

    AIRPORT_NOT_FOUND_BY_CODE(208, "Airport not found by code %s"),

    USER_NOT_FOUND_BY_USERNAME(210, "User with username %s not found"),
    SEAT_ALREADY_BOOKED_BY_FLIGHT_DATE_ROW_POSITION(215, "Seat at row: %d and position %s on a flight %s at date: %s already booked"),
    SEAT_NOT_FOUND_BY_ROW_POSITION(216, "Seat for aircraft %s id %s with row %d and position %c not found"),

    BOOKING_NOT_FOUND_BY_FLIGHT_DATE_ROW_POSITION(221, "Seat booking for flight %s on date %s with row %d and position %c not found")
    ;


    public final Integer code;
    public final String message;

    ErrorMessages(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
