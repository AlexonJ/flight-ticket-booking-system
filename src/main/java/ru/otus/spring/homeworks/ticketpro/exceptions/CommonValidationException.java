package ru.otus.spring.homeworks.ticketpro.exceptions;

import lombok.Getter;


@Getter
public class CommonValidationException extends RuntimeException {

    private Integer errorCode;

    private String message;

    public CommonValidationException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
