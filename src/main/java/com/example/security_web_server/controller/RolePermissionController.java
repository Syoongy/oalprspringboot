package com.example.security_web_server.controller;

import com.example.security_web_server.controller.controller_resource.AccessLevel;
import com.example.security_web_server.dao.PermissionDAO;
import com.example.security_web_server.dao.RolePermissionDAO;
import com.example.security_web_server.dao.impl.UserDetailsServiceImpl;
import com.example.security_web_server.model.Identity.RolePermissionIdentity;
import com.example.security_web_server.model.Permission;
import com.example.security_web_server.model.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rolepermission")
public class RolePermissionController {

  private int pid = 2;

  @Autowired
  private RolePermissionDAO myRepo;
  @Autowired
  private PermissionDAO permRepo;
  @Autowired
  private UserDetailsServiceImpl userDetailsRepo;

  @GetMapping("/getAll")
  @Transactional
  public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ.level());
	if (check) {
	  List<RolePermission> returnList = myRepo.findAll();
	  System.out.println(returnList);
	  return new ResponseEntity<>(returnList, HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @GetMapping("/getAllRoutes/{id}")
  @Transactional
  public ResponseEntity<?> getAllRoutes(@PathVariable("id") int id, @RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.NO_ACCESS.level());
	if (check) {
	  // Instantiate variables for use
	  ArrayList<String> returnList = new ArrayList<>();
	  RolePermission rp;
	  int rpid;
	  String permDesc;
	  // Retrieve wanted values
	  List<RolePermission> rpList = myRepo.findRolePermissionByRolePermissionIdentity_Roleid(id);
	  List<Permission> permList = permRepo.findAll();
	  Map<Integer, String> permissionMap = permList.stream()
			  .collect(Collectors.toMap(Permission::getPermission_id, Permission::getPermission_desc));
	  // Loop to populate returnList
	  for (RolePermission rolePermission : rpList) {
		rp = rolePermission;
		rpid = rp.getRolePermissionIdentity().getPermission_id();
		permDesc = permissionMap.get(rpid);
		// Check if the role has permission to at least access the route

		if ((rp.getAccess() > AccessLevel.NO_ACCESS.level())) {
		  returnList.add(permDesc);
		}
	  }
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
	  List<RolePermission> rolePermission = myRepo.findRolePermissionByRolePermissionIdentity_Roleid(id);
	  return new ResponseEntity<>(rolePermission, HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @PostMapping("/add")
  @Transactional
  public ResponseEntity<?> add(@RequestBody List<RolePermission> newRolePermissionList,
							   @RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ_WRITE.level());
	if (check) {
	  for (RolePermission newRole : newRolePermissionList) {
		myRepo.save(newRole);
	  }
	  return new ResponseEntity<>(HttpStatus.CREATED);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @PutMapping("/edit")
  @Transactional
  public ResponseEntity<?> update(@RequestBody List<RolePermission> updatedRolePermissionList,
								  @RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ_WRITE.level());
	if (check) {
	  for (RolePermission updatedRole : updatedRolePermissionList) {
		myRepo.save(updatedRole);
	  }
	  return new ResponseEntity<>(HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @DeleteMapping("/delete")
  @Transactional
  public ResponseEntity<?> deleteByID(@RequestBody RolePermissionIdentity id,
									  @RequestHeader("Authorization") String token) {
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
