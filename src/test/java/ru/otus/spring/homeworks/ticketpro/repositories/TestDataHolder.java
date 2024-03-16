package ru.otus.spring.homeworks.ticketpro.repositories;

import lombok.Getter;
import ru.otus.spring.homeworks.ticketpro.models.db.ScheduleRecord;
import ru.otus.spring.homeworks.ticketpro.models.db.aircraft.Aircraft;
import ru.otus.spring.homeworks.ticketpro.models.db.aircraft.enums.AircraftManufacturers;
import ru.otus.spring.homeworks.ticketpro.models.db.aircraft.enums.AircraftModels;
import ru.otus.spring.homeworks.ticketpro.models.db.airlines.Airline;
import ru.otus.spring.homeworks.ticketpro.models.db.airports.Airport;
import ru.otus.spring.homeworks.ticketpro.models.db.bookings.SeatBooking;
import ru.otus.spring.homeworks.ticketpro.models.db.flights.Flight;
import ru.otus.spring.homeworks.ticketpro.models.db.seats.Seat;
import ru.otus.spring.homeworks.ticketpro.models.db.seats.enums.SeatLocation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TestDataHolder {

    public static final LocalDateTime FLIGHT_SCHEDULE_START_DATE_TIME = LocalDateTime.of(2024, 2,15, 8, 0);
    @Getter
    private static List<Airport> airports;

    @Getter
    private static List<Airline> airlines;

    @Getter
    private static List<Flight> flights;

    @Getter
    private static List<SeatBooking> bookings;

    @Getter
    private static List<Aircraft> aircrafts;
    public static void prepareTestData() {
        airports = getDbAirports();
        airlines = getDbAirlines();
        aircrafts = getDbAircrafts();
        flights = getDbFlights();
        bookings = getDbBookings();
    }

    public static List<Airline> getDbAirlines() {
        return IntStream.range(1, 8).boxed().map(value -> {
            var airline = new Airline();
            airline.setId(value);
            airline.setName("Airline_%d".formatted(value));
            return airline;}).toList();
    }
    public static List<Airport> getDbAirports() {
        return IntStream.range(1, 8).boxed().map(value -> {
            var airport = new Airport();
            airport.setId(value);
            airport.setCity("Airport_%d_city".formatted(value));
            airport.setCode("Airport_%d_code".formatted(value));
            return airport;}).toList();
    }

    public static List<Aircraft> getDbAircrafts() {
        return IntStream.range(1, 5).boxed().map(value -> {
            var aircraft = new Aircraft();
            aircraft.setId(value);
            aircraft.setModel(AircraftModels.values()[value - 1]);
            aircraft.setManufacturer(AircraftManufacturers.BOEING);
            var seatId = 0;
            List<Seat> seats = new ArrayList<>();
            for(int row = 1; row <= 5; row++) {
                for(char position = 'A'; position <= 'F'; position ++) {
                    var seat = new Seat();
                    seat.setAircraft(aircraft);
                    seat.setId(seatId);
                    seat.setRow(row);
                    seat.setPosition(position);
                    if (position == 'A' || position == 'F') {
                        seat.setLocation(SeatLocation.WINDOW);
                    } else if (position == 'B' || position == 'E') {
                        seat.setLocation(SeatLocation.MIDDLE);
                    } else if (position == 'C' || position == 'D') {
                        seat.setLocation(SeatLocation.AISLE);
                    }
                    seats.add(seat);
                    seatId++;
                }
            }
            aircraft.setSeats(seats);
            return aircraft;
        }).toList();
    }

    public static List<Flight> getDbFlights() {
        return IntStream.range(1, 4).boxed().map(value -> {
            var flight = new Flight();
            flight.setId(value);
            flight.setNumber("FlightNumber_%d".formatted(value));
            flight.setAircraft(getAircrafts().get(value));
            flight.setDepartureAirport(getAirports().get(value));
            flight.setArrivalAirport(getAirports().get(value + 1));
            flight.setAirline(getAirlines().get(value));
            List<ScheduleRecord> flightSchedule = new ArrayList<>();
            for (int i = 0; i <= value; i++) {
                var flightScheduleRecord = new ScheduleRecord();
                flightScheduleRecord.setDepartureDateTime(FLIGHT_SCHEDULE_START_DATE_TIME.plusDays(value).plusHours(value));
                flightScheduleRecord.setArrivalDateTime(flightScheduleRecord.getDepartureDateTime().plusHours(8));
                flightSchedule.add(flightScheduleRecord);
            }
            flight.setSchedule(flightSchedule);
            return flight;
        }).toList();
    }

    public static List<SeatBooking> getDbBookings() {
        List<SeatBooking> seatBookings = new ArrayList<>();
        Long seatBookingId = 1L;
        for (Flight flight : getDbFlights()) {
            for (Seat seat : flight.getAircraft().getSeats()) {
                if (seat.getId() % 2 == 0) {
                    continue;
                }
                var seatBooking = new SeatBooking();
                seatBooking.setFlight(flight);
                seatBooking.setSeat(seat);
                seatBooking.setFlightDate(flight.getSchedule().get(0).getDepartureDateTime().toLocalDate());
                seatBooking.setBookingDate(FLIGHT_SCHEDULE_START_DATE_TIME.plusDays(5).plusHours(seat.getId()));
                seatBookings.add(seatBooking);
                seatBookingId++;
            }
        }
        return seatBookings;
    }

    public static Aircraft getAircraftById(long id) {
        return getAircrafts().stream().filter(aircraft -> aircraft.getId() == id).findFirst().orElseThrow();
    }

    public static Airline getAirlineById(long id) {
        return getAirlines().stream().filter(airline -> airline.getId() == id).findFirst().orElseThrow();
    }

    public static Airport getAirportById(long id) {
        return getAirports().stream().filter(airport -> airport.getId() == id).findFirst().orElseThrow();
    }
    public static Flight getFlightById(long id) {
        return getFlights().stream().filter(flights -> flights.getId() == id).findFirst().orElseThrow();
    }
}
