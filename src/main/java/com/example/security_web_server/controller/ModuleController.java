package com.example.security_web_server.controller;

import com.example.security_web_server.controller.controller_resource.AccessLevel;
import com.example.security_web_server.dao.ModuleDAO;
import com.example.security_web_server.dao.impl.UserDetailsServiceImpl;
import com.example.security_web_server.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/module")
public class ModuleController {
  private int pid = 1; // Should be the same with camera access

  @Autowired
  private ModuleDAO myRepo;
  @Autowired
  private UserDetailsServiceImpl userDetailsRepo;

  @GetMapping("/getAll")
  @Transactional
  public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ.level());
	if (check) {
	  List<Module> returnList = myRepo.findAll();
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
	  Module module = myRepo.findById(id).orElse(null);
	  return new ResponseEntity<>(module, HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

//  Should not be used for modules
//  @PostMapping("/add")
//  @Transactional
//  public ResponseEntity<?> add(@RequestBody Module newModule, @RequestHeader("Authorization") String token) {
//	// Check if user is able to access resource
//	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ_WRITE.level());
//	if (check) {
//	  myRepo.save(newModule);
//	  return new ResponseEntity<>(newModule, HttpStatus.CREATED);
//	} else {
//	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	}
//  }
//
//  @PutMapping("/edit")
//  @Transactional
//  public ResponseEntity<?> update(@RequestBody Module updatedModule, @RequestHeader("Authorization") String token) {
//	// Check if user is able to access resource
//	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ_WRITE.level());
//	if (check) {
//	  myRepo.save(updatedModule);
//	  return new ResponseEntity<>(HttpStatus.OK);
//	} else {
//	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	}
//  }
//
//  @DeleteMapping(value = "/delete/{id}")
//  @Transactional
//  public ResponseEntity<?> deleteByID(@PathVariable("id") int id, @RequestHeader("Authorization") String token) {
//	// Check if user is able to access resource
//	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ_WRITE.level());
//	if (check) {
//	  myRepo.deleteById(id);
//	  return new ResponseEntity<>(HttpStatus.OK);
//	} else {
//	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	}
//  }
}

