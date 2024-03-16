package ru.otus.spring.homeworks.ticketpro.models.search;

public enum ComparisonConditions {

    EQUAL("="),
    GREATER(">"),
    LESS("<"),
    IN("in"),
    LIKE("like");

    private String sqlEquivalent;

    ComparisonConditions(String sqlEquivalent) {
        this.sqlEquivalent = sqlEquivalent;
    }

    public String getSqlEquivalent() {
        return sqlEquivalent;
    }

}
