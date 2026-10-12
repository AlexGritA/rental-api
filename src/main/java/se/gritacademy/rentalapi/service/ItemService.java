package se.gritacademy.rentalapi.service;

import org.springframework.stereotype.Service;
import se.gritacademy.rentalapi.repository.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
}
