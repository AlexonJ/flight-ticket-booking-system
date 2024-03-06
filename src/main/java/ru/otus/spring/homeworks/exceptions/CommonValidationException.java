package ru.otus.spring.homeworks.exceptions;

import lombok.Data;
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
