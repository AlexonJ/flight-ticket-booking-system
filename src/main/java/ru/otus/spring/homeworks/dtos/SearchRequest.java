package ru.otus.spring.homeworks.dtos;

import lombok.Data;
import ru.otus.spring.homeworks.models.search.SearchCondition;
import ru.otus.spring.homeworks.models.search.Searchable;

import java.util.List;

@Data
public class SearchRequest<T extends Searchable> {

    List<SearchCondition<T>> searchConditionals;

}
