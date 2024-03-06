package ru.otus.spring.homeworks.exceptions;

public class FieldValueParseException extends RuntimeException {
    public FieldValueParseException (String message){
        super(message);
    }
}
