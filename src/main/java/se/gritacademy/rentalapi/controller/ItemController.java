package se.gritacademy.rentalapi.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.gritacademy.rentalapi.model.Item;
import se.gritacademy.rentalapi.repository.RentalRepository;
import se.gritacademy.rentalapi.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(@Valid @RequestBody Item item) {
        return itemService.createItem(item);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @Valid @RequestBody Item updatedItem) {
        return itemService.updateItem(id, updatedItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemById(@PathVariable Long id) {
        itemService.deleteItemById(id);
    }
}
