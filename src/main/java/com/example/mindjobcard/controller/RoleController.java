package com.example.mindjobcard.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mindjobcard.exception.ResourceNotFoundException;
import com.example.mindjobcard.model.Role;
import com.example.mindjobcard.model.User;
import com.example.mindjobcard.service.RoleService;
import com.example.mindjobcard.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/role")
	public ResponseEntity<List<Role>> getAllRole() {
		
		List<Role> role = new ArrayList<Role>();
		
		roleService.getAllRole().forEach(role :: add);
		
		if(role.isEmpty()) {
			return new ResponseEntity<List<Role>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Role>>(role, HttpStatus.OK);
	}
	
	@GetMapping("/roles/{id}/users")
	public ResponseEntity<List<User>> getAllUserByRole(@PathVariable int id) {
	
		if(!roleService.existsById(id)) {
			throw new ResourceNotFoundException("Role not found");
		}
		
		List<User> users = userService.findUsersByRolesId(id);
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

}
