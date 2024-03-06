package ru.otus.spring.homeworks.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.spring.homeworks.models.db.flights.Flight;

import java.time.LocalDateTime;

@Data
public class FlightScheduleDto {

    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;
}
