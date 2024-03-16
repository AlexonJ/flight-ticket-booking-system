package ru.otus.spring.homeworks.ticketpro.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.otus.spring.homeworks.ticketpro.models.db.ScheduleRecord;
import ru.otus.spring.homeworks.ticketpro.models.db.bookings.SeatBooking;

@Service
public class MessageGenerationServiceImpl implements MessageGenerationService{

    private static final String BOOKING_CONFIRMATION_SUBJECT = "Booking Confirmation for [FlightNumber] on [FlightDate]";
    private static final String BOOKING_CONFIRMATION_MESSAGE =
            "Dear [PassengersName],\n" +
                    "\n" +
                    "We are pleased to inform you that your booking for Flight [FlightNumber] on [FlightDate] has been confirmed successfully.\n" +
                    "\n" +
                    "Booking Details:\n" +
                    "Flight Number: [FlightNumber]\n" +
                    "Departure Date and Time: [DepartureDateTime]\n" +
                    "Arrival Date and Time: [ArrivalDateTime]\n" +
                    "Departure Airport: [DepartureAirport]\n" +
                    "Arrival Airport: [ArrivalAirport]\n" +
                    "Seat Number: [SeatNumber]\n" +
                    "\n" +
                    "Thank you for choosing our airline. We look forward to serving you.\n" +
                    "\n" +
                    "Best regards,\n" +
                    "[AirlineName]";

    @Override
    public SimpleMailMessage generateBookingConfirmation(SeatBooking booking, ScheduleRecord schedule) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getPassenger().getEmail());
        message.setSubject(BOOKING_CONFIRMATION_SUBJECT
                .replace("[FlightNumber]", booking.getFlight().getNumber())
                .replace("[FlightDate]", booking.getFlightDate().toString()));
        message.setText(BOOKING_CONFIRMATION_MESSAGE
                .replace("[PassengersName]", booking.getPassenger().getFirstName()
                        + " " + booking.getPassenger().getLastName())
                .replace("[FlightNumber]", booking.getFlight().getNumber())
                .replace("[FlightDate]", booking.getFlightDate().toString())
                .replace("[DepartureDateTime]", schedule.getDepartureDateTime().toString())
                .replace("[ArrivalDateTime]", schedule.getArrivalDateTime().toString())
                .replace("[DepartureAirport]", booking.getFlight().getDepartureAirport().getCity())
                .replace("[ArrivalAirport]", booking.getFlight().getArrivalAirport().getCity())
                .replace("[SeatNumber]", booking.getSeat().getRow() + booking.getSeat().getPosition().toString())
                .replace("[AirlineName]", booking.getFlight().getAirline().getName()));
        return message;
    }
}
