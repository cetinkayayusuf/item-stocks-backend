package com.cetinkayayusuf.itemstocks.business.abstracts;

import com.cetinkayayusuf.itemstocks.entities.concretes.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> getByUsername(String username);
}
