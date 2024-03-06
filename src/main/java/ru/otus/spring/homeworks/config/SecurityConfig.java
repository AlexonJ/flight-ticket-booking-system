package ru.otus.spring.homeworks.config;

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
import ru.otus.spring.homeworks.repositories.SettingsProvider;

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
        String flightsApiFullPath = basePath + settingsProvider.getFlightsPath() + "/**";
        String bookingsApiFullPath = basePath + settingsProvider.getBookingsPath() + "/**";
        String scheduleApiFullPath = basePath + settingsProvider.getSchedulePath() + "/**";
        String seatsApiFullPath = basePath + settingsProvider.getSeatsPath() + "/**";
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(authorizationManager -> authorizationManager
                        .requestMatchers("/", "/error", "h2-console/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, bookingsApiFullPath, "bookings").hasAnyAuthority(
                                "READ_BOOKINGS", "READ_BOOKINGS_OWNER")
                        .requestMatchers(HttpMethod.POST, bookingsApiFullPath).hasAuthority("CREATE_BOOKINGS")
                        .requestMatchers(HttpMethod.DELETE, bookingsApiFullPath).hasAuthority("DELETE_BOOKINGS")
                        .requestMatchers(HttpMethod.GET, flightsApiFullPath).hasAuthority("READ_FLIGHTS")
                        .requestMatchers(HttpMethod.POST, flightsApiFullPath).hasAuthority("CREATE_FLIGHTS")
                        .requestMatchers(HttpMethod.DELETE, flightsApiFullPath).hasAuthority("DELETE_FLIGHTS")
                        .requestMatchers(HttpMethod.GET, scheduleApiFullPath).hasAuthority("READ_SCHEDULE")
                        .requestMatchers(HttpMethod.POST, scheduleApiFullPath).hasAuthority("CREATE_SCHEDULE")
                        .requestMatchers(HttpMethod.DELETE, scheduleApiFullPath).hasAuthority("DELETE_SCHEDULE")
                        .requestMatchers(HttpMethod.GET, seatsApiFullPath).hasAuthority("READ_SEATS")
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
