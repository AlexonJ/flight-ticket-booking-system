package ru.otus.spring.homeworks.ticketpro.services;

import ru.otus.spring.homeworks.ticketpro.dtos.SearchRequest;
import ru.otus.spring.homeworks.ticketpro.models.search.Searchable;

import java.util.stream.Stream;

public interface EntitySearchService {

    <T> Stream<T> findEntities(SearchRequest<? extends Searchable> searchRequest, Class<T> entityClass, String additionalEntitiesString);
}
