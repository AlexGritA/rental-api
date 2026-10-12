package se.gritacademy.rentalapi.service;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import se.gritacademy.rentalapi.exception.ResourceNotFoundException;
import se.gritacademy.rentalapi.model.Item;
import se.gritacademy.rentalapi.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
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

}
