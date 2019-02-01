package com.example.security_web_server.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;

import static com.example.security_web_server.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
  public JWTAuthorizationFilter(AuthenticationManager authManager) {
	super(authManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
		  throws IOException, ServletException {
	String header = req.getHeader(HEADER_STRING);

	if (header == null || !header.startsWith(TOKEN_PREFIX)) {
	  chain.doFilter(req, res);
	  return;
	}

	UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

	SecurityContextHolder.getContext().setAuthentication(authentication);
	chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
	String token = request.getHeader(HEADER_STRING);
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
		return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
	  }
	  return null;
	}
	return null;
  }
}
