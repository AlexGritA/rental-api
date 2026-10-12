package se.gritacademy.rentalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.gritacademy.rentalapi.model.Item;

public interface ItemRepository extends JpaRepository <Item, Long> {
}
