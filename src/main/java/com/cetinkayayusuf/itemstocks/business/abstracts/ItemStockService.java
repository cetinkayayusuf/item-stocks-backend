package com.cetinkayayusuf.itemstocks.business.abstracts;

import com.cetinkayayusuf.itemstocks.controllers.dtos.stock.AddStockRequest;
import com.cetinkayayusuf.itemstocks.entities.concretes.ItemStock;

import java.util.List;
import java.util.Optional;

public interface ItemStockService {
    public List<ItemStock> getAll();
    public List<ItemStock> getAllByUserId(long userId);
    public ItemStock add(ItemStock stock);

    public Optional<ItemStock> getByIdAndUserId(Long id, Long userId);

    public ItemStock save(ItemStock stock);
    public Boolean deleteById(Long id);
    public Boolean deleteByIdAndUserId(Long id, Long userId);

    public boolean existsByItemId(Long itemId);
}
