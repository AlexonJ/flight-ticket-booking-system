package ru.otus.spring.homeworks.dtos;

import lombok.Data;
import ru.otus.spring.homeworks.models.db.aircraft.enums.AircraftManufacturers;
import ru.otus.spring.homeworks.models.db.aircraft.enums.AircraftModels;

@Data
public class AircraftDto {

    private long id;

    private AircraftManufacturers manufacturer;

    private AircraftModels model;

}
