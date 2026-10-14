package se.gritacademy.rentalapi.service;

import org.springframework.stereotype.Service;
import se.gritacademy.rentalapi.exception.ConflictException;
import se.gritacademy.rentalapi.exception.ResourceNotFoundException;
import se.gritacademy.rentalapi.model.Person;
import se.gritacademy.rentalapi.repository.PersonRepository;
import se.gritacademy.rentalapi.repository.RentalRepository;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final RentalRepository rentalRepository;

    public PersonService(PersonRepository personRepository, RentalRepository rentalRepository) {
        this.personRepository = personRepository;
        this.rentalRepository = rentalRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person with id " + id + " not found"));
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person updatedPerson) {
        Person existingPerson = getPersonById(id);

        existingPerson.setName(updatedPerson.getName());
        existingPerson.setEmail(updatedPerson.getEmail());
        existingPerson.setPhoneNumber(updatedPerson.getPhoneNumber());

        return personRepository.save(existingPerson);
    }

    public void deletePerson(Long id) {
        Person existingPerson = getPersonById(id);

        if (rentalRepository.existsByPersonId(id)) {
            throw new ConflictException("Person with id " + id + " has rental history and cannot be deleted");
        }
        personRepository.delete(existingPerson);
    }
}
