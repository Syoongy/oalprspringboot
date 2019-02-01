package com.example.security_web_server.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int role_id;

  @NotNull
  private String role_name;

  public int getRole_id() {
	return role_id;
  }

  public void setRole_id(int role_id) {
	this.role_id = role_id;
  }

  public String getRole_name() {
	return role_name;
  }

  public void setRole_name(String role_name) {
	this.role_name = role_name;
  }
}
