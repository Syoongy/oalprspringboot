package com.example.security_web_server.model;

import com.example.security_web_server.model.Identity.CameraModuleIdentity;

import javax.persistence.*;

@Entity
@Table(name = "camera_module")
public class CameraModule {
  @EmbeddedId
  private CameraModuleIdentity cameraModuleIdentity;

  public CameraModuleIdentity getCameraModuleIdentity() {
	return cameraModuleIdentity;
  }

  public void setCameraModuleIdentity(CameraModuleIdentity cameraModuleIdentity) {
	this.cameraModuleIdentity = cameraModuleIdentity;
  }

  public CameraModule(CameraModuleIdentity cameraModuleIdentity) {
	this.cameraModuleIdentity = cameraModuleIdentity;
  }

  public CameraModule() {
  }
}
