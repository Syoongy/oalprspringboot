package com.example.security_web_server.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private int user_id;

  @NotNull
  private String username;

  @NotNull
  @Column(name = "userpw")
  private String userpw;

  @NotNull
  @Column(name = "role_id")
  private int role_id;

  public int getRole_id() {
	return role_id;
  }

  public void setRole_id(int role_id) {
	this.role_id = role_id;
  }

  public int getUser_id() {
	return user_id;
  }

  public void setUser_id(int user_id) {
	this.user_id = user_id;
  }

  public String getUsername() {
	return username;
  }

  public void setUsername(String username) {
	this.username = username;
  }

  public String getUserpw() {
	return userpw;
  }

  public void setUserpw(String userpw) {
	this.userpw = userpw;
  }
}
