package com.example.security_web_server.model;

import javax.persistence.*;

@Entity
@Table(name = "camera")
public class Camera {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int camera_id;

  private  int location_id, minx, miny, maxx, maxy;
  private String camera_name, camera_location, camera_ip_address;
  private Boolean camera_is_active, camera_analytic;

  public int getMinx() {
	return minx;
  }

  public void setMinx(int minx) {
	this.minx = minx;
  }

  public int getMiny() {
	return miny;
  }

  public void setMiny(int miny) {
	this.miny = miny;
  }

  public int getMaxx() {
	return maxx;
  }

  public void setMaxx(int maxx) {
	this.maxx = maxx;
  }

  public int getMaxy() {
	return maxy;
  }

  public void setMaxy(int maxy) {
	this.maxy = maxy;
  }

  public Boolean getCamera_analytic() {
	return camera_analytic;
  }

  public void setCamera_analytic(Boolean camera_analytic) {
	this.camera_analytic = camera_analytic;
  }

  public int getCamera_id() {
	return camera_id;
  }

  public void setCamera_id(int camera_id) {
	this.camera_id = camera_id;
  }

  public int getLocation_id() {
	return location_id;
  }

  public void setLocation_id(int location_id) {
	this.location_id = location_id;
  }

  public String getCamera_name() {
	return camera_name;
  }

  public void setCamera_name(String camera_name) {
	this.camera_name = camera_name;
  }

  public String getCamera_location() {
	return camera_location;
  }

  public void setCamera_location(String camera_location) {
	this.camera_location = camera_location;
  }

  public String getCamera_ip_address() {
	return camera_ip_address;
  }

  public void setCamera_ip_address(String camera_ip_address) {
	this.camera_ip_address = camera_ip_address;
  }

  public Boolean getCamera_is_active() {
	return camera_is_active;
  }

  public void setCamera_is_active(Boolean camera_is_active) {
	this.camera_is_active = camera_is_active;
  }

}
