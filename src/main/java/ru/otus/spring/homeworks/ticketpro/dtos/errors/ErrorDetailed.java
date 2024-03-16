package ru.otus.spring.homeworks.ticketpro.dtos.errors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetailed {
    private int code;
    private String message;
}
