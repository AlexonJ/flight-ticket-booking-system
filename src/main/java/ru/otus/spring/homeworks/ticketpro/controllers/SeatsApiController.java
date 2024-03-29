package ru.otus.spring.homeworks.ticketpro.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.homeworks.ticketpro.dtos.seats.SeatSearchRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.seats.SeatSearchResponse;
import ru.otus.spring.homeworks.ticketpro.services.SeatService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/seats")
public class SeatsApiController {

    private final SeatService seatService;

    @GetMapping
    ResponseEntity<SeatSearchResponse> findSeat(@RequestBody SeatSearchRequest request) {
        return ResponseEntity.ok(seatService.findSeats(request));
    }
}
