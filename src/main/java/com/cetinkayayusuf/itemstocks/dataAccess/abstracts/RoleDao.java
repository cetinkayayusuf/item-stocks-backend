package com.cetinkayayusuf.itemstocks.dataAccess.abstracts;

import com.cetinkayayusuf.itemstocks.entities.concretes.ERole;
import com.cetinkayayusuf.itemstocks.entities.concretes.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}