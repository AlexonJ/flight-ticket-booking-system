package ru.otus.spring.homeworks.ticketpro.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.spring.homeworks.ticketpro.services.BookingService;

@RequiredArgsConstructor
@Controller
public class BookingsPageController {

    private final BookingService bookingService;

    @GetMapping("bookings")
    public String bookingsList(Model model) {
        model.addAttribute("bookings", bookingService.findAllWithFlightData());
        return "bookings-list";
    }
}
