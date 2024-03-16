package ru.otus.spring.homeworks.ticketpro.dtos.schedule;

import lombok.Data;

import java.util.List;

@Data
public class FlightScheduleModificationRequest {

    private String flightNumber;

    List<ScheduleRecordDto> schedule;

}
