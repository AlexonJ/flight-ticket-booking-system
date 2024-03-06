package ru.otus.spring.homeworks.services;

import ru.otus.spring.homeworks.dtos.SearchRequest;
import ru.otus.spring.homeworks.models.search.Searchable;

import java.util.stream.Stream;

public interface EntitySearchService {

    <T> Stream<T> findEntities(SearchRequest<? extends Searchable> searchRequest, Class<T> entityClass, String additionalEntitiesString);
}
