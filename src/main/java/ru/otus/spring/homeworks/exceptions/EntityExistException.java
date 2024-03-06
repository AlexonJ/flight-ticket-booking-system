package ru.otus.spring.homeworks.exceptions;

public class EntityExistException extends RuntimeException{
    public EntityExistException (String message) {
        super(message);
    }
}
