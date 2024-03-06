package ru.otus.spring.homeworks.dtos.flights;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.spring.homeworks.models.search.FlightSearchFields;
import ru.otus.spring.homeworks.models.search.SearchCondition;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FlightSearchRequest {

    List<SearchCondition<FlightSearchFields>> searchConditionals;

}
