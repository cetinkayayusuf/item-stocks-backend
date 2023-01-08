package com.cetinkayayusuf.itemstocks.business.abstracts;

import com.cetinkayayusuf.itemstocks.entities.concretes.RefreshToken;
import com.cetinkayayusuf.itemstocks.entities.concretes.User;

import java.util.Optional;

public interface RefreshTokenService {
    String createRefreshToken(User user);

    boolean isRefreshExpired(RefreshToken token);

    Optional<RefreshToken> getByUsername(String username);
}
