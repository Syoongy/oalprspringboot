package com.example.security_web_server.dao;

import com.example.security_web_server.model.CameraModule;
import com.example.security_web_server.model.Identity.CameraModuleIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CameraModuleDAO extends JpaRepository<CameraModule, CameraModuleIdentity> {
}
