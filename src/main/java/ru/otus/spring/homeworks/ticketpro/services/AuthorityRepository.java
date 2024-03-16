package ru.otus.spring.homeworks.ticketpro.services;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homeworks.ticketpro.models.db.Roles;
import ru.otus.spring.homeworks.ticketpro.models.db.RolesAuthorities;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<RolesAuthorities, Long> {

    List<RolesAuthorities> findAllByRole(Roles role);

}
