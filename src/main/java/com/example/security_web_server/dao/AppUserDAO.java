package com.example.security_web_server.dao;

import com.example.security_web_server.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserDAO extends JpaRepository<AppUser, Integer> {
  AppUser findByUsername(String username);
}
