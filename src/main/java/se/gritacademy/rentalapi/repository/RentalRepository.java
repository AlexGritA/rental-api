package se.gritacademy.rentalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.gritacademy.rentalapi.model.Rental;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    boolean existsByItemIdAndReturnedDateIsNull(Long itemId);

    List<Rental> findByItemId(Long itemId);
    List<Rental> findByPersonId(Long personId);

    boolean existsByItemId(Long itemId);
    boolean existsByPersonId(Long personId);
}