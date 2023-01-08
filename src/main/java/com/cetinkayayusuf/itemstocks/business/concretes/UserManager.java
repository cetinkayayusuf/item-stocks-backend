package com.cetinkayayusuf.itemstocks.business.concretes;

import com.cetinkayayusuf.itemstocks.business.abstracts.UserService;
import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.UserDao;
import com.cetinkayayusuf.itemstocks.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManager implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Optional<User> getByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
