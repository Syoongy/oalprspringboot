package com.example.security_web_server.controller.controller_resource;

public enum AccessLevel {
  NO_ACCESS(0),
  READ(1),
  READ_WRITE(2);

  private int level;

  AccessLevel(int level) {
	this.level = level;
  }

  public int level() {
	return level;
  }
}
