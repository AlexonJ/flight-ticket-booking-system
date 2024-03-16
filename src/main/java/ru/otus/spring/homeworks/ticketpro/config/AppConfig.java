package ru.otus.spring.homeworks.ticketpro.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.spring.homeworks.ticketpro.repositories.SettingsProvider;

@Data
@AllArgsConstructor
@ConfigurationProperties(prefix = "service")
public class AppConfig implements SettingsProvider {

    private String basePath;
    private String flightsPath;
    private String bookingsPath;
    private String schedulePath;
    private String seatsPath;
    private Boolean allowSendingEmail;

}
