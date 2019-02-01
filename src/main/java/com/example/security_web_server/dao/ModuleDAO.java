package com.example.security_web_server.dao;

import com.example.security_web_server.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleDAO extends JpaRepository<Module, Integer> {
}
