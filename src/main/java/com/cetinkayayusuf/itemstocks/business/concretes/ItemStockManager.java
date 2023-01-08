package com.cetinkayayusuf.itemstocks.business.concretes;

import com.cetinkayayusuf.itemstocks.business.abstracts.ItemStockService;
import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.ItemStockDao;
import com.cetinkayayusuf.itemstocks.entities.concretes.ItemStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemStockManager implements ItemStockService {
    @Autowired
    private ItemStockDao stockDao;

    @Override
    public List<ItemStock> getAll() {
        return stockDao.findAll();
    }

    @Override
    public List<ItemStock> getAllByUserId(long userId) {
        return stockDao.getAllByUserId(userId);
    }

    @Override
    public ItemStock add(ItemStock stock) {
        return stockDao.save(stock);
    }

    @Override
    public Optional<ItemStock> getByIdAndUserId(Long id, Long userId) {
        return stockDao.getByUserIdAndId(userId,id);
    }

    @Override
    public ItemStock save(ItemStock stock) {
        return stockDao.save(stock);
    }
    @Override
    public Boolean deleteById(Long id) {
        if(stockDao.existsById(id)) {
            stockDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteByIdAndUserId(Long id, Long userId) {
        if(stockDao.existsByUserIdAndId(userId, id)) {
            stockDao.deleteItemStockByUserIdAndId(userId, id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByItemId(Long itemId) {
        return stockDao.existsByItem_Id(itemId);
    }
}
