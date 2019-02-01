package com.example.security_web_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SecurityWebServerApplication implements WebMvcConfigurer {

  public static void main(String[] args) {
	SpringApplication.run(SecurityWebServerApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
	return new BCryptPasswordEncoder();
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
	registry.addMapping("/**");
  }
}
