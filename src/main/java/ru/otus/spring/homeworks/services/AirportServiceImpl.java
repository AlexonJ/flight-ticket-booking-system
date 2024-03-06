package ru.otus.spring.homeworks.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homeworks.models.db.airports.Airport;
import ru.otus.spring.homeworks.repositories.AirportRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

}
