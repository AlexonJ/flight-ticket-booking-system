package ru.otus.spring.homeworks.ticketpro.dtos.flights;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.otus.spring.homeworks.ticketpro.models.db.seats.enums.SeatLocation;

@Data
@AllArgsConstructor
@Builder
public class SeatSummaryInfo {
    private SeatLocation location;
    private Long seatsTotalAmount;
    private Long seatsBusy;
    private Long seatsFree;
}
