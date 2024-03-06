package ru.otus.spring.homeworks.models.db.aircraft;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.otus.spring.homeworks.models.db.aircraft.enums.AircraftManufacturers;
import ru.otus.spring.homeworks.models.db.aircraft.enums.AircraftModels;
import ru.otus.spring.homeworks.models.db.seats.Seat;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "aircrafts")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private AircraftManufacturers manufacturer;

    @Column
    @Enumerated(EnumType.STRING)
    private AircraftModels model;

    @OneToMany(mappedBy = "aircraft")
    List<Seat> seats;

}
