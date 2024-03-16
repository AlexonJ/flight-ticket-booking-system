package ru.otus.spring.homeworks.ticketpro.dtos;

import lombok.Data;
import ru.otus.spring.homeworks.ticketpro.models.db.aircraft.enums.AircraftManufacturers;
import ru.otus.spring.homeworks.ticketpro.models.db.aircraft.enums.AircraftModels;

@Data
public class AircraftDto {

    private long id;

    private AircraftManufacturers manufacturer;

    private AircraftModels model;

}
