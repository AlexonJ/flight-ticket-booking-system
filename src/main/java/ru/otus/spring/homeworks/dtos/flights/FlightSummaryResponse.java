package ru.otus.spring.homeworks.dtos.flights;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FlightSummaryResponse {

    private String flightNumber;

    private List<SeatSummaryInfo> seats;

    private Long seatsTotalAmount;
    private Long seatsBusy;
    private Long seatsFree;
}
