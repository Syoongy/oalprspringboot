package com.example.security_web_server.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "module")
public class Module {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int module_id;

  @NotNull
  private String module_name;

  public int getModule_id() {
	return module_id;
  }

  public void setModule_id(int module_id) {
	this.module_id = module_id;
  }

  public String getModule_name() {
	return module_name;
  }

  public void setModule_name(String module_name) {
	this.module_name = module_name;
  }
}
