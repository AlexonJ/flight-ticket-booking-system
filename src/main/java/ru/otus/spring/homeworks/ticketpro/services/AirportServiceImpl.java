package ru.otus.spring.homeworks.ticketpro.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homeworks.ticketpro.exceptions.CommonValidationException;
import ru.otus.spring.homeworks.ticketpro.mappers.DtoMapper;
import ru.otus.spring.homeworks.ticketpro.models.db.airports.Airport;
import ru.otus.spring.homeworks.ticketpro.repositories.AirportRepository;

import java.util.List;

import static ru.otus.spring.homeworks.ticketpro.exceptions.ErrorMessages.AIRPORT_NOT_FOUND_BY_CODE;

@RequiredArgsConstructor
@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    private final DtoMapper mapper;

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    @Override
    public Airport findByCodeChecked(String code) {

        return airportRepository.findByCode(code).orElseThrow(
                () -> new CommonValidationException(
                        AIRPORT_NOT_FOUND_BY_CODE.getCode(),
                        AIRPORT_NOT_FOUND_BY_CODE.getMessage().formatted(code)));
    }
}
