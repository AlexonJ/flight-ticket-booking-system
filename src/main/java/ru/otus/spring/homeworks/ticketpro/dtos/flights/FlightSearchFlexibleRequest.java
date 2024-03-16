package ru.otus.spring.homeworks.ticketpro.dtos.flights;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.spring.homeworks.ticketpro.models.search.FlightSearchFields;
import ru.otus.spring.homeworks.ticketpro.models.search.SearchCondition;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FlightSearchFlexibleRequest {

    List<SearchCondition<FlightSearchFields>> searchConditionals;

}
