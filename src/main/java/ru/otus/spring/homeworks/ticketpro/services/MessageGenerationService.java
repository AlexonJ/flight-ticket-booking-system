package ru.otus.spring.homeworks.ticketpro.services;

import org.springframework.mail.SimpleMailMessage;
import ru.otus.spring.homeworks.ticketpro.models.db.ScheduleRecord;
import ru.otus.spring.homeworks.ticketpro.models.db.bookings.SeatBooking;

public interface MessageGenerationService {
    SimpleMailMessage generateBookingConfirmation(SeatBooking booking, ScheduleRecord schedule);
}
