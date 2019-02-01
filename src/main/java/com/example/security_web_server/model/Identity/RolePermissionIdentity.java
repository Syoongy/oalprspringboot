package com.example.security_web_server.model.Identity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class RolePermissionIdentity implements Serializable {
  private static final long serialVersionUID = 8611256436204890797L;

  @NotNull
  @Column(name = "role_id")
  private int roleid;

  @NotNull
  private int permission_id;

  public RolePermissionIdentity(@NotNull int role_id, @NotNull int permission_id) {
	this.roleid = role_id;
	this.permission_id = permission_id;
  }

  public RolePermissionIdentity() {
  }

  public int getRole_id() {
	return roleid;
  }

  public void setRole_id(int role_id) {
	this.roleid = role_id;
  }

  public int getPermission_id() {
	return permission_id;
  }

  public void setPermission_id(int permission_id) {
	this.permission_id = permission_id;
  }
}
