package ru.otus.spring.homeworks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homeworks.models.db.users.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUsername(String username);

}
