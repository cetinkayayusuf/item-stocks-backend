package com.cetinkayayusuf.itemstocks.business.concretes;

import com.cetinkayayusuf.itemstocks.business.abstracts.RefreshTokenService;
import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.RefreshTokenDao;
import com.cetinkayayusuf.itemstocks.entities.concretes.RefreshToken;
import com.cetinkayayusuf.itemstocks.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenManager implements RefreshTokenService {

    @Value("${jwtRefreshTokenExpirationMs}")
    private Long jwtRefreshTokenExpirationMs;

    @Autowired
    private RefreshTokenDao refreshTokenDao;


    @Autowired
    private RefreshTokenDao userDao;

    @Override
    public String createRefreshToken(User user) {
        Optional<RefreshToken> tokenResult = refreshTokenDao.findAllByUser_Id(user.getId());
        RefreshToken token;
        if (tokenResult.isPresent()) {
            token = tokenResult.get();
        } else {
            token = new RefreshToken();
            token.setUser(user);
        }
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(jwtRefreshTokenExpirationMs)));
        refreshTokenDao.save(token);
        return token.getToken();
    }

    @Override
    public boolean isRefreshExpired(RefreshToken token) {
        return token.getExpiryDate().before(new Date());
    }

    @Override
    public Optional<RefreshToken> getByUsername(String username) {
        return refreshTokenDao.findByUser_Username(username);
    }
}
