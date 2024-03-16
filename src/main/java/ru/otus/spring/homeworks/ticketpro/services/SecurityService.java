package ru.otus.spring.homeworks.ticketpro.services;

import ru.otus.spring.homeworks.ticketpro.models.db.users.User;

public interface SecurityService {
    User getCurrentUser();
}
