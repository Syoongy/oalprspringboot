package com.example.security_web_server.model;

import javax.persistence.*;

@Entity
@Table(name = "permission")
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int permission_id;

  private String permission_desc;

  public int getPermission_id() {
	return permission_id;
  }

  public void setPermission_id(int permission_id) {
	this.permission_id = permission_id;
  }

  public String getPermission_desc() {
	return permission_desc;
  }

  public void setPermission_desc(String permission_desc) {
	this.permission_desc = permission_desc;
  }
}
