package ru.otus.spring.homeworks.services;

import ru.otus.spring.homeworks.models.db.users.User;

public interface UserService {

    User getUserByUsernameChecked(String username);
}
