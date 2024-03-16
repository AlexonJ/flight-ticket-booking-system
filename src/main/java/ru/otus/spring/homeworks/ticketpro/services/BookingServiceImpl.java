package ru.otus.spring.homeworks.ticketpro.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homeworks.ticketpro.dtos.SearchRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.bookings.SeatBookingCreationRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.bookings.SeatBookingDto;
import ru.otus.spring.homeworks.ticketpro.dtos.bookings.SeatBookingDtoWithFlightData;
import ru.otus.spring.homeworks.ticketpro.exceptions.CommonValidationException;
import ru.otus.spring.homeworks.ticketpro.mappers.DtoMapper;
import ru.otus.spring.homeworks.ticketpro.models.db.ScheduleRecord;
import ru.otus.spring.homeworks.ticketpro.models.db.bookings.SeatBooking;
import ru.otus.spring.homeworks.ticketpro.models.db.flights.Flight;
import ru.otus.spring.homeworks.ticketpro.models.db.seats.Seat;
import ru.otus.spring.homeworks.ticketpro.models.db.users.User;
import ru.otus.spring.homeworks.ticketpro.models.search.BookingSearchFields;
import ru.otus.spring.homeworks.ticketpro.repositories.BookingRepository;
import ru.otus.spring.homeworks.ticketpro.repositories.ScheduleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.otus.spring.homeworks.ticketpro.exceptions.ErrorMessages.*;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final FlightService flightService;

    private final SeatService seatService;

    private final UserService userService;

    private final BookingRepository bookingRepository;

    private final SecurityService securityService;

    private final EntitySearchService entitySearchService;

    private final EmailSendingService emailSendingService;

    private final MessageGenerationService messageGenerationService;

    private final ScheduleRepository scheduleRepository;

    private final DtoMapper mapper;

    @Override
    @PostFilter(value = "hasAuthority('READ_BOOKINGS_OWNER') && filterObject.user.username == authentication.name || hasAuthority('READ_BOOKINGS')")
    public List<SeatBookingDto> findBookings(SearchRequest<BookingSearchFields> searchRequest) {

        var seatBookingDtos = entitySearchService.findEntities(searchRequest, SeatBooking.class, null)
                .map(mapper::seatBookingToSeatBookingDto).collect(Collectors.toList());
        return seatBookingDtos;
    }

    @Override
    public List<SeatBookingDtoWithFlightData> findAllWithFlightData() {
        return bookingRepository.findAll().stream().map(mapper::seatBookingToSeatBookingDtoWithFlightData).toList();
    }

    @Transactional
    @Override
    public SeatBookingDto createBooking(SeatBookingCreationRequest request) {

        Flight flight = flightService.getFlightByNumberAndDateChecked(request.getFlightNumber(), request.getFlightDate());

        User user = securityService.getCurrentUser();
        Seat seat = seatService.getSeatByAircraftRowPositionChecked(flight.getAircraft(),
                request.getSeatRow(), request.getSeatPosition());

        if (seatIsBookedChecked(flight, seat, request.getFlightDate())) {
            throw new CommonValidationException(
                    SEAT_ALREADY_BOOKED_BY_FLIGHT_DATE_ROW_POSITION.getCode(),
                    SEAT_ALREADY_BOOKED_BY_FLIGHT_DATE_ROW_POSITION.getMessage()
                            .formatted(seat.getRow(), seat.getPosition(), flight.getNumber(), request.getFlightDate()));
        }

        SeatBooking booking = new SeatBooking(0, flight, request.getFlightDate(), seat,
                LocalDateTime.now(), mapper.passengerDtoToPassenger(request.getPassenger()), user);

        ScheduleRecord schedule = scheduleRepository.findFirstByFlightAndDepartureDate(flight, booking.getFlightDate())
                .orElseThrow(() -> new CommonValidationException(
                        SCHEDULE_NOT_FOUND_FOR_FLIGHT_BY_DATE.getCode(),
                        SCHEDULE_NOT_FOUND_FOR_FLIGHT_BY_DATE.getMessage()));

        SimpleMailMessage message = messageGenerationService.generateBookingConfirmation(booking, schedule);
        emailSendingService.sendSimpleEmail(message);

        return Optional.of(bookingRepository.save(booking)).map(mapper::seatBookingToSeatBookingDto).get();

    }

    @Override
    public void deleteBooking(String flightNumber, LocalDate flightDate, Integer seatRow, Character seatPosition) {
        Flight flight = flightService.getFlightByNumberAndDateChecked(flightNumber, flightDate);
        Seat seat = seatService.getSeatByAircraftRowPositionChecked(flight.getAircraft(), seatRow, seatPosition);
        SeatBooking seatBooking = getSeatBookingChecked(flight, flightDate, seat);
        bookingRepository.delete(seatBooking);
    }

    @Override
    public SeatBooking getSeatBookingChecked(Flight flight, LocalDate flightDate, Seat seat) {
        return bookingRepository.findFirstByFlightAndFlightDateAndSeat(flight, flightDate, seat).orElseThrow(
                () -> new CommonValidationException(
                        BOOKING_NOT_FOUND_BY_FLIGHT_DATE_ROW_POSITION.getCode(),
                        BOOKING_NOT_FOUND_BY_FLIGHT_DATE_ROW_POSITION.getMessage().formatted(
                        flight.getNumber(), flightDate, seat.getRow(), seat.getPosition())));
    }

    @Override
    public boolean seatIsBookedChecked(Flight flight, Seat seat, LocalDate flightDate){
        return bookingRepository.findFirstByFlightAndFlightDateAndSeat(flight, flightDate, seat)
                .isPresent();
    }

}
