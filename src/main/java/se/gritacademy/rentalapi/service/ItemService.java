package se.gritacademy.rentalapi.service;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import se.gritacademy.rentalapi.exception.ConflictException;
import se.gritacademy.rentalapi.exception.ResourceNotFoundException;
import se.gritacademy.rentalapi.model.Item;
import se.gritacademy.rentalapi.repository.ItemRepository;
import se.gritacademy.rentalapi.repository.RentalRepository;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final RentalRepository rentalRepository;

    public ItemService(ItemRepository itemRepository, RentalRepository rentalRepository) {
        this.itemRepository = itemRepository;
        this.rentalRepository = rentalRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + id + " not found"));
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item updatedItem) {
        Item existingItem = getItemById(id);

        existingItem.setName(updatedItem.getName());
        existingItem.setDescription(updatedItem.getDescription());
        existingItem.setCategory(updatedItem.getCategory());
        existingItem.setPricePerDay(updatedItem.getPricePerDay());

        return itemRepository.save(existingItem);
    }

    public void deleteItemById(Long id) {
        Item existingItem = getItemById(id);

        if (rentalRepository.existsByItemId(id)) {
            throw new ConflictException("Item with id " + id + " has rental history and cannot be deleted");
        }
        itemRepository.delete(existingItem);
    }
}
