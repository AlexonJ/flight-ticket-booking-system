package ru.otus.spring.homeworks.ticketpro.dtos.schedule;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleRecordDto {

    private long id;

    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;
}
