package se.gritacademy.rentalapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.gritacademy.rentalapi.dto.RentalRequest;
import se.gritacademy.rentalapi.exception.ConflictException;
import se.gritacademy.rentalapi.exception.ResourceNotFoundException;
import se.gritacademy.rentalapi.model.Item;
import se.gritacademy.rentalapi.model.Person;
import se.gritacademy.rentalapi.model.Rental;
import se.gritacademy.rentalapi.repository.RentalRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final PersonService personService;
    private final ItemService itemService;

    public RentalService(RentalRepository rentalRepository,
                         PersonService personService,
                         ItemService itemService) {
        this.rentalRepository = rentalRepository;
        this.personService = personService;
        this.itemService = itemService;
    }

    // Hämta alla uthyrningar
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    // Hämta en uthyrning (404 om den inte finns)
    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental with id " + id + " not found"));
    }

    // Historik: alla uthyrningar för en sak
    public List<Rental> getRentalsByItemId(Long itemId) {
        itemService.getItemById(itemId);   // 404 om saken inte finns
        return rentalRepository.findByItemId(itemId);
    }

    // Historik: alla uthyrningar för en person
    public List<Rental> getRentalsByPersonId(Long personId) {
        personService.getPersonById(personId);   // 404 om personen inte finns
        return rentalRepository.findByPersonId(personId);
    }

    // Skapa en uthyrning
    @Transactional
    public Rental createRental(RentalRequest request) {
        // 1. Hämta person och sak (404 om någon saknas)
        Person person = personService.getPersonById(request.getPersonId());
        Item item = itemService.getItemById(request.getItemId());

        // 2. Ingen dubbelhyrning (409)
        if (rentalRepository.existsByItemIdAndReturnedDateIsNull(item.getId())) {
            throw new ConflictException("Item with id " + item.getId() + " is already rented out");
        }

        // 3. Bygg och spara uthyrningen
        Rental rental = new Rental();
        rental.setPerson(person);
        rental.setItem(item);
        rental.setStartDate(request.getStartDate());
        rental.setEndDate(request.getEndDate());

        return rentalRepository.save(rental);
    }

    // Lämna tillbaka en sak
    @Transactional
    public Rental returnRental(Long id) {
        Rental rental = getRentalById(id);   // 404 om den inte finns

        if (rental.getReturnedDate() != null) {
            throw new ConflictException("Rental with id " + id + " is already returned");
        }

        rental.setReturnedDate(LocalDate.now());
        return rentalRepository.save(rental);
    }
}