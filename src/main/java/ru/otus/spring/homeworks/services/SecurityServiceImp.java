package ru.otus.spring.homeworks.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.otus.spring.homeworks.exceptions.CommonValidationException;
import ru.otus.spring.homeworks.exceptions.ErrorMessages;
import ru.otus.spring.homeworks.models.db.users.User;
import ru.otus.spring.homeworks.repositories.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SecurityServiceImp implements SecurityService {

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username;
        Optional<User> user = Optional.empty();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails userDetails) {
                username = userDetails.getUsername();
                user = userRepository.findFirstByUsername(username);
            } else {
                username = null;
            }
        } else {
            username = null;
        }

        return user.orElseThrow(() -> new CommonValidationException(
                ErrorMessages.USER_NOT_FOUND_BY_USERNAME.getCode(),
                ErrorMessages.USER_NOT_FOUND_BY_USERNAME.getMessage().formatted(username)));
    }
}