package ru.otus.spring.homeworks.services;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.h2.util.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.homeworks.dtos.SearchRequest;
import ru.otus.spring.homeworks.exceptions.FieldValueParseException;
import ru.otus.spring.homeworks.models.search.Searchable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EntitySearchServiceImpl implements EntitySearchService {

    private static final String WHERE_CONDITIONAL_TEMPLATE = "%s%s %s ?%s";

    private static final String SELECT_FROM_TEMPLATE = "SELECT %s FROM %s %s";

    private final EntityManager entityManager;

    public <T> Stream<T> findEntities(SearchRequest<? extends Searchable> searchRequest, Class<T> entityClass, String additionalEntitiesString) {

        var classSimpleName = entityClass.getSimpleName();
        var classSimpleNameLowCamel = upperCamelToLowerCamel(classSimpleName);

        StringBuilder sqlRequest = new StringBuilder(SELECT_FROM_TEMPLATE.formatted(
                classSimpleNameLowCamel, classSimpleName, classSimpleNameLowCamel));

        if (!StringUtils.isNullOrEmpty(additionalEntitiesString)) {
            sqlRequest.append(additionalEntitiesString);
        }
        StringBuilder whereConditions = new StringBuilder();
        List<Object> parameters = new ArrayList<>();

        for (int i = 0; i < searchRequest.getSearchConditionals().size(); i++) {

            var condition = searchRequest.getSearchConditionals().get(i);
            if (condition.getValues().size() == 1) {
                var value = condition.getValues().stream().findFirst().get();
                try {
                    parameters.add(convertStringToObject(value, condition.getField().getFieldClass()));
                } catch (NumberFormatException | DateTimeParseException ex) {
                    throw new FieldValueParseException(ex.getMessage());
                }

            }

            whereConditions.append(WHERE_CONDITIONAL_TEMPLATE.formatted(
                    whereConditions.isEmpty() ? "" : " AND ",
                    condition.getField().getPath(),
                    condition.getCondition().getSqlEquivalent(),
                    i + 1));

        }

        sqlRequest.append(" WHERE ").append(whereConditions);

        var query = entityManager.createQuery(sqlRequest.toString(), entityClass);
        for (int i = 0; i < parameters.size(); i++) {
            query.setParameter(i + 1, parameters.get(i));
        }

        return query.getResultList().stream();
    }

    private Object convertStringToObject(String stringValue, Class<?> clazz) {

        if (clazz.equals(Integer.class)) {
            return Integer.parseInt(stringValue);
        } else if (clazz.equals(Double.class)) {
            return Double.parseDouble(stringValue);
        } else if (clazz.equals(LocalDateTime.class)) {
            return LocalDateTime.parse(stringValue);
        } else if (clazz.equals(LocalDate.class)) {
            return LocalDate.parse(stringValue);
        } else if (clazz.equals(String.class)) {
            return stringValue;
        }

        return null;
    }

    public String upperCamelToLowerCamel(String sourceString) {
        if (sourceString == null || sourceString.isEmpty()) {
            return sourceString;
        }
        char firstChar = Character.toLowerCase(sourceString.charAt(0));
        return firstChar + sourceString.substring(1);
    }
}
