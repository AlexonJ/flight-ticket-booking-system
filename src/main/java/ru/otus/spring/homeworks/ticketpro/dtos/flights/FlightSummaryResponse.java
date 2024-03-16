package ru.otus.spring.homeworks.ticketpro.dtos.flights;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FlightSummaryResponse {

    private String flightNumber;

    private List<SeatSummaryInfo> seats;

    private Long seatsTotalAmount;
    private Long seatsBusy;
    private Long seatsFree;
}
