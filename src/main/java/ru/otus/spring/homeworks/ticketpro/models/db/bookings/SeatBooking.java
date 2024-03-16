package ru.otus.spring.homeworks.ticketpro.models.db.bookings;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.spring.homeworks.ticketpro.models.db.Passenger;
import ru.otus.spring.homeworks.ticketpro.models.db.flights.Flight;
import ru.otus.spring.homeworks.ticketpro.models.db.seats.Seat;
import ru.otus.spring.homeworks.ticketpro.models.db.users.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "seat_bookings")
public class SeatBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Flight flight;

    @Column
    private LocalDate flightDate;

    @ManyToOne
    private Seat seat;

    @Column
    private LocalDateTime bookingDate;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Passenger passenger;

    @ManyToOne
    private User user;

}
