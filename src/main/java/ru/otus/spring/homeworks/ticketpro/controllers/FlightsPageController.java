package ru.otus.spring.homeworks.ticketpro.controllers;

import lombok.RequiredArgsConstructor;
import org.h2.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightSearchFixedRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightWithScheduleDto;
import ru.otus.spring.homeworks.ticketpro.services.AirportService;
import ru.otus.spring.homeworks.ticketpro.services.FlightService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class FlightsPageController {

    private final FlightService flightService;
    private final AirportService airportService;

    @GetMapping("flights")
    public String flightList(@RequestParam(required = false) String departureAirportCode,
                             @RequestParam(required = false) String arrivalAirportCode,
                             @RequestParam(required = false) LocalDate flightDate,
                             Model model) {
        FlightSearchFixedRequest request = new FlightSearchFixedRequest();
        request.setDepartureAirportCode(departureAirportCode);
        request.setArrivalAirportCode(arrivalAirportCode);
        request.setFlightDate(flightDate);

        model.addAttribute("searchRequest", request);
        model.addAttribute("airports", airportService.findAll());
        List<FlightWithScheduleDto> flightList = new ArrayList<>();
        if (!StringUtils.isNullOrEmpty(request.getDepartureAirportCode())
                || !StringUtils.isNullOrEmpty(request.getArrivalAirportCode())
                || !Objects.isNull(flightDate)) {
            flightList = flightService.getFlightsWithSchedule(request).getResult();
        }
        model.addAttribute("flights", flightList);
        return "flights-list";
    }

//    @PostMapping("/books/edit")
//    public String saveBook(@Valid @ModelAttribute("book") BookDtoIds book,
//                           BindingResult bindingResult,
//                           @RequestParam("newCommentContent") String newCommentContent,
//                           Model model
//    ) {
//        if (bindingResult.hasErrors()) {
//            fillDataInModel(model, book.getId());
//            return "book-edit";
//        }
//
//        var savedBookId = bookService.update(book.getId(), book.getTitle(), book.getAuthorId(),
//                book.getGenreIds(), book.getCommentIds()).getId();
//
//        if (!StringUtils.isNullOrEmpty(newCommentContent)) {
//            commentService.insert(savedBookId, newCommentContent);
//        }
//
//        return "redirect:/books";
//    }
}