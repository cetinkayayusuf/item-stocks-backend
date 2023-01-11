package com.cetinkayayusuf.itemstocks.controllers;

import com.cetinkayayusuf.itemstocks.business.abstracts.ItemService;
import com.cetinkayayusuf.itemstocks.business.abstracts.ItemStockService;
import com.cetinkayayusuf.itemstocks.business.abstracts.UserService;
import com.cetinkayayusuf.itemstocks.controllers.dtos.stock.AddStockRequest;
import com.cetinkayayusuf.itemstocks.controllers.dtos.stock.UpdateStockRequest;
import com.cetinkayayusuf.itemstocks.entities.concretes.ItemStock;
import com.cetinkayayusuf.itemstocks.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/stocks")
public class ItemStockController {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemStockService stockService;

    @PostMapping(value="/", consumes={MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> add(@RequestBody AddStockRequest addStockRequest) {
        var user = getCurrentUser();
        if(user.isEmpty())
        {
            System.out.println("User Empty");
            return ResponseEntity
                    .badRequest()
                    .body("Error: User does not exist!");
        }

        Long itemId = addStockRequest.getItemId();

        if (stockService.existsByItemIdAndUserId(itemId, user.get().getId())) {
            System.out.println("Stock Fail");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Stock with same item exist!");
        }

        var item = itemService.getById(itemId);
        if (item.isEmpty()) {
            System.out.println("Item Fail");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Item does not exist!");
        }

        var stock = new ItemStock();
        stock.setItem(item.get());
        stock.setAmount(addStockRequest.getAmount());
        stock.setName(addStockRequest.getName());
        stock.setDescription(addStockRequest.getDescription());


        stock.setUserId(user.get().getId());

        var stockResult = stockService.add(stock);
        if (stockResult != null)
            System.out.println("Success");
        if (stockResult != null)
            return ResponseEntity.ok(stockResult);

        System.out.println("Fail");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> getAll() {
        var user = getCurrentUser();
        if(user.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body("Error: User does not exist!");

        return ResponseEntity.ok(stockService.getAllByUserId(user.get().getId()));
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> get(@PathVariable Long id) {
        var user = getCurrentUser();
        if(user.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body("Error: User does not exist!");

        var stock = stockService.getByIdAndUserId(id, user.get().getId());
        if (stock.isPresent())
            return ResponseEntity.ok(stock);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/item/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> getByItemId(@PathVariable Long id) {
        var user = getCurrentUser();
        if(user.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body("Error: User does not exist!");

        var stock = stockService.getByItemIdAndUserId(id, user.get().getId());
        if (stock.isPresent())
            return ResponseEntity.ok(stock);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateStockRequest updateStockRequest) {
        var user = getCurrentUser();
        if(user.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body("Error: User does not exist!");

        var stock = stockService.getByIdAndUserId(id, user.get().getId());
        if (stock.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body("Error: Not found Stock!");
        var updatedStock = stock.get();

        if(updateStockRequest.getName() != null)
            updatedStock.setName(updateStockRequest.getName());
        if(updateStockRequest.getDescription() != null)
            updatedStock.setDescription(updateStockRequest.getDescription());
        updatedStock.setAmount(updateStockRequest.getAmount());

        var savedStock = stockService.save(updatedStock);
        if (savedStock != null) {
            savedStock.setUserId(0);
            return ResponseEntity.ok(savedStock);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var user = getCurrentUser();
        if(user.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body("Error: User does not exist!");

        if (stockService.deleteByIdAndUserId(id, user.get().getId()))
            return ResponseEntity.ok("Success");

        return ResponseEntity
                .badRequest()
                .body("Error: Not found item!");
    }

    private Optional<User> getCurrentUser()
    {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userService.getByUsername(username);
    }
}