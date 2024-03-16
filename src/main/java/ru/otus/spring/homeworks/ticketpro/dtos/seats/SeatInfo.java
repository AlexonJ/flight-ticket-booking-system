package ru.otus.spring.homeworks.ticketpro.dtos.seats;

import lombok.Builder;
import lombok.Data;
import ru.otus.spring.homeworks.ticketpro.models.db.seats.enums.SeatLocation;

@Data
@Builder
public class SeatInfo {

    private SeatLocation location;

    private Long row;

    private Character position;

    private Boolean isFree;

}