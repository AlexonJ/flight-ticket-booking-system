package ru.otus.spring.homeworks.ticketpro.dtos.seats;

import lombok.Data;
import ru.otus.spring.homeworks.ticketpro.models.db.seats.enums.SeatLocation;

@Data
public class SeatDto {

    private long row;

    private Character position;

    private SeatLocation location;

}
