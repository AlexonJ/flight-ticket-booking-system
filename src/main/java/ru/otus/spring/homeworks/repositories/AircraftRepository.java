package ru.otus.spring.homeworks.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.homeworks.models.db.aircraft.Aircraft;

//@RepositoryRestResource(path = "aircrafts")
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

}
