package com.example.security_web_server.model;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int location_id;
  private String location_name, location_area, location_type, location_address, location_postalcode;

  public int getLocation_id() {
	return location_id;
  }

  public void setLocation_id(int location_id) {
	this.location_id = location_id;
  }

  public String getLocation_name() {
	return location_name;
  }

  public void setLocation_name(String location_name) {
	this.location_name = location_name;
  }

  public String getLocation_area() {
	return location_area;
  }

  public void setLocation_area(String location_area) {
	this.location_area = location_area;
  }

  public String getLocation_type() {
	return location_type;
  }

  public void setLocation_type(String location_type) {
	this.location_type = location_type;
  }

  public String getLocation_address() {
	return location_address;
  }

  public void setLocation_address(String location_address) {
	this.location_address = location_address;
  }

  public String getLocation_postalcode() {
	return location_postalcode;
  }

  public void setLocation_postalcode(String location_postalcode) {
	this.location_postalcode = location_postalcode;
  }
}
