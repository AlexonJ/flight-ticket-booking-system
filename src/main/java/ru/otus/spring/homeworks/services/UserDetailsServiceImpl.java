package ru.otus.spring.homeworks.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.homeworks.exceptions.CommonValidationException;
import ru.otus.spring.homeworks.models.db.RolesAuthorities;
import ru.otus.spring.homeworks.models.db.users.User;
import ru.otus.spring.homeworks.repositories.UserRepository;

import java.util.List;

import static ru.otus.spring.homeworks.exceptions.ErrorMessages.USER_NOT_FOUND_BY_USERNAME;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new CommonValidationException(
                        USER_NOT_FOUND_BY_USERNAME.getCode(),
                        USER_NOT_FOUND_BY_USERNAME.getMessage().formatted(username)));

        List<RolesAuthorities> authorities = authorityRepository.findAllByRole(user.getRole());
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority().toString())).toList());
    }
}
