package com.example.security_web_server.model;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
public class Schedule {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int schedule_id;

  private int cam_id, day;
  private String start_time, end_time;
  private Boolean forever;

  public Schedule(int schedule_id, int cam_id, int day, String start_time, String end_time, Boolean forever) {
	this.schedule_id = schedule_id;
	this.cam_id = cam_id;
	this.day = day;
	this.start_time = start_time;
	this.end_time = end_time;
	this.forever = forever;
  }

  public Schedule(int schedule_id, int day, String start_time, String end_time, Boolean forever) {
	this.schedule_id = schedule_id;
	this.day = day;
	this.start_time = start_time;
	this.end_time = end_time;
	this.forever = forever;
  }

  public Schedule() {
  }

  public int getSchedule_id() {
	return schedule_id;
  }

  public void setSchedule_id(int schedule_id) {
	this.schedule_id = schedule_id;
  }

  public int getCam_id() {
	return cam_id;
  }

  public void setCam_id(int cam_id) {
	this.cam_id = cam_id;
  }

  public int getDay() {
	return day;
  }

  public void setDay(int day) {
	this.day = day;
  }

  public String getStart_time() {
	return start_time;
  }

  public void setStart_time(String start_time) {
	this.start_time = start_time;
  }

  public String getEnd_time() {
	return end_time;
  }

  public void setEnd_time(String end_time) {
	this.end_time = end_time;
  }

  public Boolean getForever() {
	return forever;
  }

  public void setForever(Boolean forever) {
	this.forever = forever;
  }
}
