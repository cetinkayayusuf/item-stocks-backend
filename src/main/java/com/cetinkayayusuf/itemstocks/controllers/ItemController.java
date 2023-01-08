package com.cetinkayayusuf.itemstocks.controllers;

import com.cetinkayayusuf.itemstocks.business.abstracts.ItemService;
import com.cetinkayayusuf.itemstocks.controllers.dtos.item.AddItemRequest;
import com.cetinkayayusuf.itemstocks.entities.concretes.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        if(item != null)
            return ResponseEntity.ok(item);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    @PreAuthorize(value = "hasAuthority('USER') or hasAuthority('ADMIN')")
    public String getAll() {
        return "All Items";
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('USER') or hasAuthority('ADMIN')")
    public String get(@PathVariable String id) {
        return "Get Item.";
    }

    @PatchMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public String update(@PathVariable String id) {
        return "Update Item.";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public String delete(@PathVariable String id) {
        return "Delete Item.";
    }
}