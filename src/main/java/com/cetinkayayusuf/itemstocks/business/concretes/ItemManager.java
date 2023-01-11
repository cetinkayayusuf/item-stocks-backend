package com.cetinkayayusuf.itemstocks.business.concretes;

import com.cetinkayayusuf.itemstocks.business.abstracts.ItemService;
import com.cetinkayayusuf.itemstocks.controllers.dtos.item.AddItemRequest;
import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.ItemDao;
import com.cetinkayayusuf.itemstocks.entities.concretes.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemManager implements ItemService {
    @Autowired
    private ItemDao itemDao;

    @Override
    public List<Item> getAll() {
        return itemDao.findAll();
    }

    @Override
    public List<Item> searchAllByName(String name) {
        return itemDao.findByNameLike("%"+name+"%");
    }

    @Override
    public Item addItem(AddItemRequest itemRequest) {
        var item =  new Item();
        item.setCode(itemRequest.getCode());
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        return itemDao.save(item);
    }

    @Override
    public Optional<Item> getById(Long id) {
        return itemDao.findById(id);
    }

    @Override
    public Item save(Item item) {
        return itemDao.save(item);
    }

    @Override
    public Boolean deleteById(Long id) {
        if(itemDao.existsById(id)) {
            itemDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByCode(String code) {
        return itemDao.existsByCode(code);
    }
}
