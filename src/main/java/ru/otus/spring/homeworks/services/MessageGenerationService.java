package ru.otus.spring.homeworks.services;

import org.springframework.mail.SimpleMailMessage;
import ru.otus.spring.homeworks.models.db.ScheduleRecord;
import ru.otus.spring.homeworks.models.db.bookings.SeatBooking;

public interface MessageGenerationService {
    SimpleMailMessage generateBookingConfirmation(SeatBooking booking, ScheduleRecord schedule);
}
