package com.cetinkayayusuf.itemstocks.dataAccess.abstracts;

import com.cetinkayayusuf.itemstocks.entities.concretes.ItemStock;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface ItemStockDao extends JpaRepository<ItemStock, Long> {
    Boolean existsByItem_Id(Long itemId);
    Boolean existsByUserIdAndId(Long userId, Long id);
    Optional<ItemStock> getByUserIdAndId(Long userId, Long id);
    List<ItemStock> getAllByUserId(Long userId);

    void deleteItemStockByUserIdAndId(Long userId, Long id);
}
