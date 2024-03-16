package ru.otus.spring.homeworks.ticketpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homeworks.ticketpro.models.db.users.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUsername(String username);

}
