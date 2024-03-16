package ru.otus.spring.homeworks.ticketpro.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightScheduleDto {

    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;
}
