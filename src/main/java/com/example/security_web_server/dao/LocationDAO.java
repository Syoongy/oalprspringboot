package com.example.security_web_server.dao;

import com.example.security_web_server.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationDAO extends JpaRepository<Location, Integer> {
}
