package com.cetinkayayusuf.itemstocks;

import com.cetinkayayusuf.itemstocks.business.abstracts.ItemService;
import com.cetinkayayusuf.itemstocks.business.abstracts.ItemStockService;
import com.cetinkayayusuf.itemstocks.business.abstracts.UserService;
import com.cetinkayayusuf.itemstocks.controllers.dtos.item.AddItemRequest;
import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.ItemDao;
import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.ItemStockDao;
import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.RoleDao;
import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.UserDao;
import com.cetinkayayusuf.itemstocks.entities.concretes.ERole;
import com.cetinkayayusuf.itemstocks.entities.concretes.Role;
import com.cetinkayayusuf.itemstocks.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ItemStocksApplication implements CommandLineRunner {

    @Autowired
    RoleDao roleDao;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemStockService stockService;

    @Autowired
    UserDao userDao;

    public static void main(String[] args) {
        SpringApplication.run(ItemStocksApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
		for (ERole roleType : ERole.values()) {
            var result = roleDao.findByName(roleType);
            if(result.isEmpty()) {
                Role role = new Role();
                role.setName(roleType);
                roleDao.save(role);
            }
		}


        for(int i = 1; i < 10; i ++)
        {
            if(!itemService.existsByCode("i-code-" + i))
                itemService.addItem(new AddItemRequest("i-code-"+i, "i-name-"+i, "i-description-"+i));
        }
    }
}
