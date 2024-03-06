package ru.otus.spring.homeworks.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PassengerDto {

    @NotBlank(message = "Passenger's first name cannot be empty")
    private String firstName;

    @NotBlank(message = "Passenger's last name cannot be empty")
    private String lastName;

    @NotBlank(message = "Passenger's document number cannot be empty")
    private String documentNumber;

    @NotBlank(message = "Passenger's telephone number cannot be empty")
    private String telephoneNumber;

    private String email;

}
