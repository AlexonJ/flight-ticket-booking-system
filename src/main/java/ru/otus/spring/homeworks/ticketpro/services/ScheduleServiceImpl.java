package ru.otus.spring.homeworks.ticketpro.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homeworks.ticketpro.dtos.schedule.ScheduleRecordDto;
import ru.otus.spring.homeworks.ticketpro.exceptions.CommonValidationException;
import ru.otus.spring.homeworks.ticketpro.exceptions.ErrorMessages;
import ru.otus.spring.homeworks.ticketpro.mappers.DtoMapper;
import ru.otus.spring.homeworks.ticketpro.models.db.ScheduleRecord;
import ru.otus.spring.homeworks.ticketpro.models.db.flights.Flight;
import ru.otus.spring.homeworks.ticketpro.repositories.BookingRepository;
import ru.otus.spring.homeworks.ticketpro.repositories.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final FlightService flightService;

    private final BookingRepository bookingRepository;

    private final DtoMapper mapper;

    @Override
    public List<ScheduleRecordDto> getFlightSchedule(String flightNumber) {
        Flight flight = flightService.getFlightByNumberChecked(flightNumber);
        return scheduleRepository.findAllByFlightNumber(flightNumber).stream().map(mapper::scheduleRecordToScheduleRecordDto).toList();
    }

    @Override
    public List<ScheduleRecordDto> modifyFlightSchedule(String flightNumber, List<ScheduleRecordDto> scheduleDtos) {
        Flight flight = flightService.getFlightByNumberChecked(flightNumber);
        List<ScheduleRecord> schedule = scheduleDtos.stream()
                .map(mapper::scheduleRecordDtoToScheduleRecord).peek(scheduleRecord -> scheduleRecord.setFlight(flight)).toList();
        return scheduleRepository.saveAll(schedule).stream().map(mapper::scheduleRecordToScheduleRecordDto).toList();
    }

    @Override
    public List<ScheduleRecordDto> deleteFlightSchedule(String flightNumber, List<LocalDateTime> departureDateTimes) {
        Flight flight = flightService.getFlightByNumberChecked(flightNumber);
        List<ScheduleRecord> schedule = scheduleRepository.findAllByFlightNumber(flight.getNumber());
        for (LocalDateTime departureDateTime : departureDateTimes) {
            if (schedule.stream().noneMatch(scheduleRecord -> scheduleRecord.getDepartureDateTime().equals(departureDateTime))) {
                throw new CommonValidationException(ErrorMessages.SCHEDULE_NOT_FOUND_FOR_FLIGHT_BY_DATE.getCode(),
                        ErrorMessages.SCHEDULE_NOT_FOUND_FOR_FLIGHT_BY_DATE.getMessage().formatted(flightNumber, departureDateTime));
            } else if (bookingRepository.flightHasAnyBookings(flight.getNumber(), departureDateTime.toLocalDate())) {
                throw new CommonValidationException(ErrorMessages.FLIGHT_HAS_BOOKINGS.getCode(),
                        ErrorMessages.FLIGHT_HAS_BOOKINGS.getMessage().formatted(flightNumber, departureDateTime));
            }

        }
        return scheduleRepository.deleteAllByFlightAndDepartureDateTimeIn(flight, departureDateTimes).stream()
                .map(mapper::scheduleRecordToScheduleRecordDto).toList();

    }

}
