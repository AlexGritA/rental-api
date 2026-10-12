package se.gritacademy.rentalapi.service;

import org.springframework.stereotype.Service;
import se.gritacademy.rentalapi.model.Person;
import se.gritacademy.rentalapi.repository.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

}
