package ru.otus.spring.homeworks.ticketpro.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.homeworks.ticketpro.exceptions.CommonValidationException;
import ru.otus.spring.homeworks.ticketpro.models.db.RolesAuthorities;
import ru.otus.spring.homeworks.ticketpro.models.db.users.User;
import ru.otus.spring.homeworks.ticketpro.repositories.UserRepository;
import ru.otus.spring.homeworks.ticketpro.exceptions.ErrorMessages;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new CommonValidationException(
                        ErrorMessages.USER_NOT_FOUND_BY_USERNAME.getCode(),
                        ErrorMessages.USER_NOT_FOUND_BY_USERNAME.getMessage().formatted(username)));

        List<RolesAuthorities> authorities = authorityRepository.findAllByRole(user.getRole());
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority().toString())).toList());
    }
}
