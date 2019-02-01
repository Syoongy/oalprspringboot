package com.example.security_web_server.dao.impl;

import com.example.security_web_server.dao.AppUserDAO;
import com.example.security_web_server.dao.RolePermissionDAO;
import com.example.security_web_server.model.AppUser;
import com.example.security_web_server.model.Identity.RolePermissionIdentity;
import com.example.security_web_server.security.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;

import static com.example.security_web_server.security.SecurityConstants.SECRET;
import static com.example.security_web_server.security.SecurityConstants.TOKEN_PREFIX;
import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private AppUserDAO appUserRepository;
  @Autowired
  private RolePermissionDAO rolePermissionRepository;

  public UserDetailsServiceImpl(AppUserDAO applicationUserRepository) {
	this.appUserRepository = applicationUserRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	AppUser appUser = appUserRepository.findByUsername(username);
	if (appUser == null) {
	  throw new UsernameNotFoundException(username);
	}
	SecurityUser user = new SecurityUser(appUser.getUsername(), appUser.getUserpw(), true, true, true, true, emptyList(), appUser.getRole_id());
//		return new User(appUser.getUsername(), appUser.getUserpw(), emptyList());
	return user;
  }

  public Boolean checkAccess(String token, int pid, int accessLevel) throws UsernameNotFoundException {
	if (token != null) {

	  // Encode our secret key
	  Key myEncodedKey = Keys.hmacShaKeyFor(SECRET.getBytes());

	  Jws<Claims> jws;
	  String user = null;

	  try {

		// parse the token
		jws = Jwts.parser().setSigningKey(myEncodedKey).parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
		user = jws.getBody().getSubject();

	  } catch (JwtException ex) {
		// we *cannot* use the JWT as intended by its creator
	  }
	  // we can safely trust the JWT
	  if (user != null) {
		int rid = appUserRepository.findByUsername(user).getRole_id();
		RolePermissionIdentity id = new RolePermissionIdentity(rid, pid);
		int roleAccessLevel = rolePermissionRepository.findById(id).get().getAccess();
		return rolePermissionRepository.findById(id).isPresent() && (roleAccessLevel >= accessLevel);
	  } else {
		throw new UsernameNotFoundException(null);
	  }
	}
	return false;
  }
}
