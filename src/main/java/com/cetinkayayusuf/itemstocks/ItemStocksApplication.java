package com.cetinkayayusuf.itemstocks;

import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.RoleDao;
import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.UserDao;
import com.cetinkayayusuf.itemstocks.entities.concretes.ERole;
import com.cetinkayayusuf.itemstocks.entities.concretes.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItemStocksApplication implements CommandLineRunner {

    @Autowired
    RoleDao roleDao;

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
    }
}
