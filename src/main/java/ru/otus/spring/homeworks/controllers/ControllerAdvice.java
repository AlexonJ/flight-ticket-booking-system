package ru.otus.spring.homeworks.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.otus.spring.homeworks.dtos.errors.ErrorDetailed;
import ru.otus.spring.homeworks.dtos.errors.ErrorResponse;
import ru.otus.spring.homeworks.exceptions.CommonValidationException;
import ru.otus.spring.homeworks.exceptions.EntityExistException;
import ru.otus.spring.homeworks.exceptions.EntityNotFoundException;
import ru.otus.spring.homeworks.exceptions.FieldValueParseException;

import java.util.stream.Collectors;

import static ru.otus.spring.homeworks.exceptions.ErrorMessages.ERROR_VALIDATING_REQUEST;
import static ru.otus.spring.homeworks.exceptions.ErrorMessages.UNABLE_TO_READ_INPUT_MESSAGE;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({
            FieldValueParseException.class,
            EntityExistException.class,
            EntityNotFoundException.class,
            CommonValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValueParseException(RuntimeException ex) {

        var errorDetailed = ErrorDetailed.builder();

        if(ex instanceof EntityExistException) {
            errorDetailed.code(102);
        } else if (ex instanceof FieldValueParseException) {
            errorDetailed.code(101);
        } else if (ex instanceof CommonValidationException) {
            errorDetailed.code(((CommonValidationException)ex).getErrorCode());
        }

        errorDetailed.message(ex.getMessage());
        var errorResponse = new ErrorResponse(errorDetailed.build());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValueParseException(MethodArgumentNotValidException ex) {

        String errorMessage = ERROR_VALIDATING_REQUEST.getMessage() + ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "Field: %s, value: %s, message: %s".formatted(
                        fieldError.getField(),
                        fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : "null",
                        fieldError.getDefaultMessage())).collect(Collectors.joining("\n"));

        var errorDetailed = ErrorDetailed.builder()
                .code(ERROR_VALIDATING_REQUEST.getCode())
                .message(errorMessage).build();
        return ResponseEntity.badRequest().body(new ErrorResponse(errorDetailed));

    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleValueParseException(HttpMessageNotReadableException ex) {
        var errorDetailed = ErrorDetailed.builder()
                .message(UNABLE_TO_READ_INPUT_MESSAGE.getMessage() + ex.getMessage())
                .code(UNABLE_TO_READ_INPUT_MESSAGE.getCode()).build();
        return ResponseEntity.badRequest().body(new ErrorResponse(errorDetailed));

    }
}
