package com.example.security_web_server.controller;

import com.example.security_web_server.controller.controller_resource.AccessLevel;
import com.example.security_web_server.dao.RoleDAO;
import com.example.security_web_server.dao.impl.UserDetailsServiceImpl;
import com.example.security_web_server.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

  private int pid = 2;

  @Autowired
  private RoleDAO myRepo;
  @Autowired
  private UserDetailsServiceImpl userDetailsRepo;

  @GetMapping("/getAll")
  @Transactional
  public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ.level());
	if (check) {
	  List<Role> returnList = myRepo.findAll();
	  System.out.println(returnList);
	  return new ResponseEntity<>(returnList, HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @GetMapping("/get/{id}")
  @Transactional
  public ResponseEntity<?> getByID(@PathVariable("id") int id, @RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ.level());
	if (check) {
	  Role role = myRepo.findById(id).orElse(null);
	  return new ResponseEntity<>(role, HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @PostMapping("/add")
  @Transactional
  public ResponseEntity<?> add(@RequestBody Role newRole, @RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ_WRITE.level());
	if (check) {
	  myRepo.save(newRole);
	  return new ResponseEntity<>(newRole, HttpStatus.CREATED);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @PutMapping("/edit")
  @Transactional
  public ResponseEntity<?> update(@RequestBody Role updatedRole, @RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ_WRITE.level());
	if (check) {
	  myRepo.save(updatedRole);
	  return new ResponseEntity<>(HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @DeleteMapping(value = "/delete/{id}")
  @Transactional
  public ResponseEntity<?> deleteByID(@PathVariable("id") int id, @RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ_WRITE.level());
	if (check) {
	  myRepo.deleteById(id);
	  return new ResponseEntity<>(HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }
}
