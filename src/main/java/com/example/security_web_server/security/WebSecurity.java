package com.example.security_web_server.security;

import com.example.security_web_server.dao.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.example.security_web_server.security.SecurityConstants.SIGN_UP_URL;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private UserDetailsServiceImpl userDetailsService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public WebSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
	this.userDetailsService = userDetailsService;
	this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
	http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
			.anyRequest().authenticated().and().addFilter(new JWTAuthenticationFilter(authenticationManager()))
			.addFilter(new JWTAuthorizationFilter(authenticationManager()))
			// this disables session creation on Spring Security
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
	CorsConfiguration myConfig = new CorsConfiguration().applyPermitDefaultValues();
	myConfig.addAllowedOrigin("*");
	myConfig.addAllowedMethod("GET");
	myConfig.addAllowedMethod("PUT");
	myConfig.addAllowedMethod("POST");
	myConfig.addAllowedMethod("DELETE");
	myConfig.addAllowedMethod("OPTIONS");
	myConfig.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers",
			"Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
					+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, roleid"));
	final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	source.registerCorsConfiguration("/**", myConfig);
	return source;
  }
}
