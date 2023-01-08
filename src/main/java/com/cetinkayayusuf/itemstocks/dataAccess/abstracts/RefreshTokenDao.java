package com.cetinkayayusuf.itemstocks.dataAccess.abstracts;

import com.cetinkayayusuf.itemstocks.entities.concretes.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenDao extends JpaRepository<RefreshToken, Long> {
    public Optional<RefreshToken> findAllByUser_Id(Long userId);
    public Optional<RefreshToken> findByUser_Username(String username);
}