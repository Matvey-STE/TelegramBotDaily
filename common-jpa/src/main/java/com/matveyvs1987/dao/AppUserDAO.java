package com.matveyvs1987.dao;

import com.matveyvs1987.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppUserDAO extends JpaRepository<AppUser,Long> {
    AppUser findAppUserByTelegramUserId(Long id);
}
