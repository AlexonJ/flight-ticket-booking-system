package ru.otus.spring.homeworks.dtos.seats;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class SeatSearchResponse {

    private String flightNumber;

    private LocalDate flightDate;

    private List<SeatInfo> seats;

}
