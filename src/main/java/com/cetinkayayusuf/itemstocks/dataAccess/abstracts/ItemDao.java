package com.cetinkayayusuf.itemstocks.dataAccess.abstracts;

import com.cetinkayayusuf.itemstocks.entities.concretes.Item;
import com.cetinkayayusuf.itemstocks.entities.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemDao extends JpaRepository<Item, Long> {
    Boolean existsByCode(String code);
}
