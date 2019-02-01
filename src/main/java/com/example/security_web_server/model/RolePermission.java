package com.example.security_web_server.model;

import com.example.security_web_server.model.Identity.RolePermissionIdentity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role_permission")
public class RolePermission {

  @EmbeddedId
  private RolePermissionIdentity rolePermissionIdentity;

  @NotNull
  private int access;

  public int getAccess() {
	return access;
  }

  public void setAccess(int access) {
	this.access = access;
  }

  public RolePermissionIdentity getRolePermissionIdentity() {
	return rolePermissionIdentity;
  }

  public void setRolePermissionIdentity(RolePermissionIdentity rolePermissionIdentity) {
	this.rolePermissionIdentity = rolePermissionIdentity;
  }
}
