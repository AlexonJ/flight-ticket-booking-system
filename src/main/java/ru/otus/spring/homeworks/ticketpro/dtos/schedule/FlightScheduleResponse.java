package ru.otus.spring.homeworks.ticketpro.dtos.schedule;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FlightScheduleResponse {

    String flightNumber;

    List<ScheduleRecordDto> flightSchedule;

}
