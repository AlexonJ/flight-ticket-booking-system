package ru.otus.spring.homeworks.ticketpro.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homeworks.ticketpro.exceptions.CommonValidationException;
import ru.otus.spring.homeworks.ticketpro.models.db.users.User;
import ru.otus.spring.homeworks.ticketpro.repositories.UserRepository;
import ru.otus.spring.homeworks.ticketpro.exceptions.ErrorMessages;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User getUserByUsernameChecked(String username) {
        return userRepository.findFirstByUsername(username).orElseThrow(() ->
                new CommonValidationException(
                        ErrorMessages.USER_NOT_FOUND_BY_USERNAME.getCode(),
                        ErrorMessages.USER_NOT_FOUND_BY_USERNAME.getMessage().formatted(username)));
    }
}
