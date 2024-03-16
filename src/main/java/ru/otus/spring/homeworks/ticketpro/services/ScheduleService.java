package ru.otus.spring.homeworks.ticketpro.services;

import ru.otus.spring.homeworks.ticketpro.dtos.schedule.ScheduleRecordDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    List<ScheduleRecordDto> getFlightSchedule(String flightNumber);

    List<ScheduleRecordDto> modifyFlightSchedule(String flightNumber, List<ScheduleRecordDto> scheduleDto);

    List<ScheduleRecordDto> deleteFlightSchedule(String flightNumber, List<LocalDateTime> localDateTimes);
}
