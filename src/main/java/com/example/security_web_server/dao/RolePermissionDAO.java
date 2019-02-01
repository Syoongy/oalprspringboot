package com.example.security_web_server.dao;

import com.example.security_web_server.model.Identity.RolePermissionIdentity;
import com.example.security_web_server.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionDAO extends JpaRepository<RolePermission, RolePermissionIdentity> {
  List<RolePermission> findRolePermissionByRolePermissionIdentity_Roleid(int role_id);
}
