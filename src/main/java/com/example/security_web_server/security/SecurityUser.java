package com.example.security_web_server.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUser extends User {

  /**
   *
   */
  private static final long serialVersionUID = -7890084043665928656L;

  private final int role_id;

  public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired,
					  boolean credentialsNonExpired, boolean accountNonLocked,
					  Collection<? extends GrantedAuthority> authorities, int roleid) {
	super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	this.role_id = roleid;
  }

  public int getRole_id() {
	return role_id;
  }

}
