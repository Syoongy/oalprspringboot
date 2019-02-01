package com.example.security_web_server.dao;

import com.example.security_web_server.model.Camera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CameraDAO extends JpaRepository<Camera, Integer> {
}
