package com.example.security_web_server.dao;

import com.example.security_web_server.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionDAO extends JpaRepository<Permission, Integer> {

}
