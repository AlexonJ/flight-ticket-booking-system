package ru.otus.spring.homeworks.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.homeworks.dtos.SearchRequest;
import ru.otus.spring.homeworks.dtos.SearchResponse;
import ru.otus.spring.homeworks.dtos.bookings.SeatBookingCreationRequest;
import ru.otus.spring.homeworks.dtos.bookings.SeatBookingDeletionRequest;
import ru.otus.spring.homeworks.dtos.bookings.SeatBookingDto;
import ru.otus.spring.homeworks.models.search.BookingSearchFields;
import ru.otus.spring.homeworks.services.BookingService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/bookings")
public class BookingsApiController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<SearchResponse<SeatBookingDto>> findSeatBooking(@RequestBody @Validated SearchRequest<BookingSearchFields> request) {

        var seatBookingDtos = bookingService.findBookings(request);

        SearchResponse<SeatBookingDto> seatBookingSearchResponse = new SearchResponse<>();
        seatBookingSearchResponse.setResult(seatBookingDtos);

        return ResponseEntity.ok(seatBookingSearchResponse);

    }

    @PostMapping
    public ResponseEntity<SeatBookingDto> createSeatBooking(@RequestBody @Validated SeatBookingCreationRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(request));

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSeatBooking(@RequestBody @Validated SeatBookingDeletionRequest request) {

        bookingService.deleteBooking(request.getFlightNumber(), request.getFlightDate(), request.getSeatRow(), request.getSeatPosition());
        return ResponseEntity.ok().build();
    }
}
