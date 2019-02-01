package com.example.security_web_server.dao;

import com.example.security_web_server.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Integer> {
}