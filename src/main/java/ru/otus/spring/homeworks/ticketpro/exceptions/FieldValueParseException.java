package ru.otus.spring.homeworks.ticketpro.exceptions;

public class FieldValueParseException extends RuntimeException {
    public FieldValueParseException (String message){
        super(message);
    }
}
