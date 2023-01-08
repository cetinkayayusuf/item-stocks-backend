package com.cetinkayayusuf.itemstocks.business.abstracts;

import com.cetinkayayusuf.itemstocks.controllers.dtos.item.AddItemRequest;
import com.cetinkayayusuf.itemstocks.entities.concretes.Item;

public interface ItemService {
    public Item addItem(AddItemRequest itemRequest);

    public boolean existsByCode(String code);
}
