package ru.otus.spring.homeworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.homeworks.config.AppConfig;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties(AppConfig.class)
public class TicketBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketBookingApplication.class);

        System.out.printf("Main page is here: %n%s%n",
                "http://localhost:8080");
        System.out.printf("H2 console here: %n%s%n",
                "http://localhost:8080/h2-console");
        System.out.printf("Actuator health page here: %n%s%n",
                "http://localhost:8080/actuator/health");
        System.out.printf("HAL explorer here: %n%s%n",
                "http://localhost:8080/api-rest/explorer");
    }
}