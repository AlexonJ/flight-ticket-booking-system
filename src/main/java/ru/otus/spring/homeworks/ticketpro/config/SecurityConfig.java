package ru.otus.spring.homeworks.ticketpro.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import ru.otus.spring.homeworks.ticketpro.repositories.SettingsProvider;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final SettingsProvider settingsProvider;

    @Bean
    public SecurityFilterChain securityFilterChainForApi(HttpSecurity httpSecurity) throws Exception {
        String basePath = settingsProvider.getBasePath();
        String[] bookingsApiPatterns = {basePath + settingsProvider.getBookingsPath() + "/**", "bookings"};
        String[] flightsApiPatterns = {basePath + settingsProvider.getFlightsPath() + "/**", "flights"};
        String[] scheduleApiPatterns = {basePath + settingsProvider.getSchedulePath() + "/**", "schedule"};
        String[] seatsApiPatterns = {basePath + settingsProvider.getSeatsPath() + "/**", "seats"};
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(authorizationManager -> authorizationManager
                        .requestMatchers("/error/**", "h2-console/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, bookingsApiPatterns).hasAnyAuthority(
                                "READ_BOOKINGS", "READ_BOOKINGS_OWNER")
                        .requestMatchers(HttpMethod.POST, bookingsApiPatterns).hasAuthority("CREATE_BOOKINGS")
                        .requestMatchers(HttpMethod.DELETE, bookingsApiPatterns).hasAuthority("DELETE_BOOKINGS")
                        .requestMatchers(HttpMethod.GET, flightsApiPatterns).hasAuthority("READ_FLIGHTS")
                        .requestMatchers(HttpMethod.POST, flightsApiPatterns).hasAuthority("CREATE_FLIGHTS")
                        .requestMatchers(HttpMethod.DELETE, flightsApiPatterns).hasAuthority("DELETE_FLIGHTS")
                        .requestMatchers(HttpMethod.GET, scheduleApiPatterns).hasAuthority("READ_SCHEDULE")
                        .requestMatchers(HttpMethod.POST, scheduleApiPatterns).hasAuthority("CREATE_SCHEDULE")
                        .requestMatchers(HttpMethod.DELETE, scheduleApiPatterns).hasAuthority("DELETE_SCHEDULE")
                        .requestMatchers(HttpMethod.GET, seatsApiPatterns).hasAuthority("READ_SEATS")
                        .anyRequest().denyAll())
                .userDetailsService(userDetailsService)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
