package ru.otus.spring.homeworks.ticketpro.exceptions;

public class EntityExistException extends RuntimeException{
    public EntityExistException (String message) {
        super(message);
    }
}
