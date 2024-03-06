package ru.otus.spring.homeworks.models.db.flights;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.spring.homeworks.models.db.aircraft.Aircraft;
import ru.otus.spring.homeworks.models.db.airlines.Airline;
import ru.otus.spring.homeworks.models.db.airports.Airport;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String number;

    @ManyToOne
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @ManyToOne
    private Airport departureAirport;

    @ManyToOne
    private Airport arrivalAirport;

//    @Column
//    private LocalDateTime departureDateTime;
//
//    @Column
//    private LocalDateTime arrivalDateTime;

//    @OneToMany(mappedBy = "flight")
//    private List<FlightSchedule> schedule;
}
