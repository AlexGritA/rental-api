package se.gritacademy.rentalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.gritacademy.rentalapi.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
