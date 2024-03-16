package ru.otus.spring.homeworks.ticketpro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.homeworks.ticketpro.config.AppConfig;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties(AppConfig.class)
public class TicketProApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketProApp.class);
    public static void main(String[] args) {

        SpringApplication.run(TicketProApp.class);

        LOGGER.info("Main page is here: {}", "http://localhost:8080");
        LOGGER.info("H2 console here: {}", "http://localhost:8080/h2-console");
        LOGGER.info("Actuator health page here: {}", "http://localhost:8080/actuator/health");
        LOGGER.info("HAL explorer here: {}", "http://localhost:8080/api-rest/explorer/");
    }
}