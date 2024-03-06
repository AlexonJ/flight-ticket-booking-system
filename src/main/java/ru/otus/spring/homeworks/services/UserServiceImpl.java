package ru.otus.spring.homeworks.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homeworks.exceptions.CommonValidationException;
import ru.otus.spring.homeworks.models.db.users.User;
import ru.otus.spring.homeworks.repositories.UserRepository;

import static ru.otus.spring.homeworks.exceptions.ErrorMessages.USER_NOT_FOUND_BY_USERNAME;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User getUserByUsernameChecked(String username) {
        return userRepository.findFirstByUsername(username).orElseThrow(() ->
                new CommonValidationException(
                        USER_NOT_FOUND_BY_USERNAME.getCode(),
                        USER_NOT_FOUND_BY_USERNAME.getMessage().formatted(username)));
    }
}
