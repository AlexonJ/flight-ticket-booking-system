package ru.otus.spring.homeworks.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class SeatPositionCharacterValidator implements ConstraintValidator<ValidSeatPositionCharacter, Character> {

    @Override
    public void initialize(ValidSeatPositionCharacter constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Character character, ConstraintValidatorContext constraintValidatorContext) {
        return !Objects.isNull(character) && character >= 'A' && character <= 'Z';
    }
}
