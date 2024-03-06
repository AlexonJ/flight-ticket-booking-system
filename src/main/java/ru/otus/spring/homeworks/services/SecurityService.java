package ru.otus.spring.homeworks.services;

import ru.otus.spring.homeworks.models.db.users.User;

public interface SecurityService {
    User getCurrentUser();
}
