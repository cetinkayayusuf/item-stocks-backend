package com.cetinkayayusuf.itemstocks.controllers;

import com.cetinkayayusuf.itemstocks.business.abstracts.ItemService;
import com.cetinkayayusuf.itemstocks.controllers.dtos.item.AddItemRequest;
import com.cetinkayayusuf.itemstocks.controllers.dtos.item.UpdateItemRequest;
import com.cetinkayayusuf.itemstocks.controllers.dtos.stock.SearchStockRequest;
import com.cetinkayayusuf.itemstocks.entities.concretes.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<?> add(@RequestBody AddItemRequest addItemRequest) {
        if (itemService.existsByCode(addItemRequest.getCode())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Code is already taken!");
        }
        var item = itemService.addItem(addItemRequest);
        if (item != null)
            return ResponseEntity.ok(item);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    @PreAuthorize(value = "hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(itemService.getAll());
    }

    @PostMapping("/search")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> search(@RequestBody SearchStockRequest searchStockRequest) {
        return ResponseEntity.ok(itemService.searchAllByName(searchStockRequest.getName()));
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> get(@PathVariable Long id) {
        var item = itemService.getById(id);
        if (item.isPresent())
            return ResponseEntity.ok(item);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateItemRequest updateItemRequest) {
        var item = itemService.getById(id);
        if (item.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body("Error: Not found item!");
        var updatedItem = item.get();
        if (updateItemRequest.getCode() != null) {
            boolean isNewCode = !Objects.equals(updateItemRequest.getCode(), updatedItem.getCode());
            if (isNewCode && itemService.existsByCode(updateItemRequest.getCode())) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Code is already taken!");
            }
            updatedItem.setCode(updateItemRequest.getCode());
        }

        if (updateItemRequest.getName() != null)
            updatedItem.setName(updateItemRequest.getName());

        if (updateItemRequest.getDescription() != null)
            updatedItem.setDescription(updateItemRequest.getDescription());

        var savedItem = itemService.save(updatedItem);
        if (savedItem != null)
            return ResponseEntity.ok(savedItem);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (itemService.deleteById(id))
            return ResponseEntity.ok("Success");

        return ResponseEntity
                .badRequest()
                .body("Error: Not found item!");
    }
}