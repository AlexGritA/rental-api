package se.gritacademy.rentalapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.gritacademy.rentalapi.dto.RentalRequest;
import se.gritacademy.rentalapi.model.Rental;
import se.gritacademy.rentalapi.service.RentalService;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/{id}")
    public Rental getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }

    @GetMapping("/item/{itemId}")
    public List<Rental> getRentalsByItem(@PathVariable Long itemId) {
        return rentalService.getRentalsByItemId(itemId);
    }

    @GetMapping("/person/{personId}")
    public List<Rental> getRentalsByPerson(@PathVariable Long personId) {
        return rentalService.getRentalsByPersonId(personId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rental createRental(@Valid @RequestBody RentalRequest request) {
        return rentalService.createRental(request);
    }

    @PutMapping("/{id}/return")
    public Rental returnRental(@PathVariable Long id) {
        return rentalService.returnRental(id);
    }
}