package com.example.security_web_server.security;

import com.example.security_web_server.model.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.security_web_server.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
		  throws AuthenticationException {
	try {
	  AppUser creds = new ObjectMapper().readValue(req.getInputStream(), AppUser.class);

	  return authenticationManager.authenticate(
			  new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getUserpw(), new ArrayList<>()));
	} catch (IOException e) {
	  throw new RuntimeException(e);
	}
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
										  Authentication auth) {
	//Encode our secret key
	Key myEncodedKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	cal.add(Calendar.DATE, 10);
	//cal.add(Calendar.MINUTE, 1);
	Date expDate = cal.getTime();
	//Instantiate user details for response
	SecurityUser user = (SecurityUser) auth.getPrincipal();
	//Generate our JWT Token
	String token = Jwts.builder().setSubject(user.getUsername())
			.setExpiration(expDate).setIssuedAt(new Date())
			.signWith(myEncodedKey).compact();
	res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	res.addIntHeader("roleid", user.getRole_id());
  }
}
