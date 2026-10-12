package se.gritacademy.rentalapi.service;

import org.springframework.stereotype.Service;
import se.gritacademy.rentalapi.exception.ResourceNotFoundException;
import se.gritacademy.rentalapi.model.Person;
import se.gritacademy.rentalapi.repository.PersonRepository;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person med id " + id + " hittades inte"));
    }

}
