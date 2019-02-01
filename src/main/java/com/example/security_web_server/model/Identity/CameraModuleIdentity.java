package com.example.security_web_server.model.Identity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class CameraModuleIdentity implements Serializable {
  @NotNull
  @Column(name = "camera_id")
  private int cameraid;

  @NotNull
  @Column(name = "module_id")
  private int moduleid;

  public CameraModuleIdentity(@NotNull int cameraid, @NotNull int moduleid) {
	this.cameraid = cameraid;
	this.moduleid = moduleid;
  }

  public CameraModuleIdentity() {
  }

  public int getCameraid() {
	return cameraid;
  }

  public void setCameraid(int cameraid) {
	this.cameraid = cameraid;
  }

  public int getModuleid() {
	return moduleid;
  }

  public void setModuleid(int moduleid) {
	this.moduleid = moduleid;
  }
}
