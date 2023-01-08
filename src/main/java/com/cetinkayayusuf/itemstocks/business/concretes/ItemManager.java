package com.cetinkayayusuf.itemstocks.business.concretes;

import com.cetinkayayusuf.itemstocks.business.abstracts.ItemService;
import com.cetinkayayusuf.itemstocks.controllers.dtos.item.AddItemRequest;
import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.ItemDao;
import com.cetinkayayusuf.itemstocks.entities.concretes.Item;
import com.cetinkayayusuf.itemstocks.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemManager implements ItemService {
    @Autowired
    private ItemDao itemDao;

    @Override
    public Item addItem(AddItemRequest itemRequest) {
        var item =  new Item();
        item.setCode(itemRequest.getCode());
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        return itemDao.save(item);
    }

    @Override
    public boolean existsByCode(String code) {
        return itemDao.existsByCode(code);
    }
}
