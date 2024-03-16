package ru.otus.spring.homeworks.ticketpro.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.homeworks.ticketpro.dtos.schedule.FlightScheduleDeletionRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.schedule.FlightScheduleModificationRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.schedule.FlightScheduleResponse;
import ru.otus.spring.homeworks.ticketpro.dtos.schedule.ScheduleRecordDto;
import ru.otus.spring.homeworks.ticketpro.services.ScheduleService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleApiController {

    private final ScheduleService scheduleService;

    @GetMapping("/{flightNumber}")
    public ResponseEntity<FlightScheduleResponse> getFlightSchedule(@PathVariable String flightNumber) {
        return ResponseEntity.ok(FlightScheduleResponse.builder()
                .flightNumber(flightNumber)
                .flightSchedule(scheduleService.getFlightSchedule(flightNumber)).build());
    }

    @PostMapping
    public ResponseEntity<FlightScheduleResponse> modifyFlightSchedule(@RequestBody @Validated FlightScheduleModificationRequest request) {
        return ResponseEntity.ok(FlightScheduleResponse.builder()
                .flightNumber(request.getFlightNumber())
                .flightSchedule(scheduleService.modifyFlightSchedule(request.getFlightNumber(), request.getSchedule())).build());
    }

    @DeleteMapping
    public ResponseEntity<List<ScheduleRecordDto>> deleteFlightSchedule(@RequestBody @Validated FlightScheduleDeletionRequest request) {
        return ResponseEntity.ok(scheduleService.deleteFlightSchedule(request.getFlightNumber(), request.getFlightDateTimeDepartures()));
    }
}
