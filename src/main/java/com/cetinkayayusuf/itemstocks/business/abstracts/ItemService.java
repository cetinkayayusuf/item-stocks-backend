package com.cetinkayayusuf.itemstocks.business.abstracts;

import com.cetinkayayusuf.itemstocks.controllers.dtos.item.AddItemRequest;
import com.cetinkayayusuf.itemstocks.entities.concretes.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    public List<Item> getAll();
    public Item addItem(AddItemRequest itemRequest);

    public Optional<Item> getById(Long id);

    public Item save(Item item);
    public Boolean deleteById(Long id);

    public boolean existsByCode(String code);
}
