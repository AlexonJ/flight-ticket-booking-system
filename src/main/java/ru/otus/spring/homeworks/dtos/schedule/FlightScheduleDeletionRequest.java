package ru.otus.spring.homeworks.dtos.schedule;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FlightScheduleDeletionRequest {

    private String flightNumber;

    List<LocalDateTime> flightDateTimeDepartures;

}
