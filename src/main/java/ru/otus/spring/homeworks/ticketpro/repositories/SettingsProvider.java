package ru.otus.spring.homeworks.ticketpro.repositories;

public interface SettingsProvider {

    String getBasePath();

    String getFlightsPath();

    String getBookingsPath();

    String getSchedulePath();

    String getSeatsPath();
}
