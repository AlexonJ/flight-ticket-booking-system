package ru.otus.spring.homeworks.ticketpro.dtos;

import lombok.Data;
import ru.otus.spring.homeworks.ticketpro.models.search.SearchCondition;
import ru.otus.spring.homeworks.ticketpro.models.search.Searchable;

import java.util.List;

@Data
public class SearchRequest<T extends Searchable> {

    List<SearchCondition<T>> searchConditionals;

}
