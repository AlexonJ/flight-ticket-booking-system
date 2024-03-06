package ru.otus.spring.homeworks.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.otus.spring.homeworks.models.db.flights.Flight;

import java.util.List;

@Data
public class SearchResponse<T> {

    List<T> result;

}
