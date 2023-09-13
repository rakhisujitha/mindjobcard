package com.example.mindjobcard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mindjobcard.dto.MessageResponse;
import com.example.mindjobcard.exception.ResourceNotFoundException;
import com.example.mindjobcard.model.Role;
import com.example.mindjobcard.model.User;
import com.example.mindjobcard.service.RoleService;
import com.example.mindjobcard.service.UserService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/user")
	public ResponseEntity<List<User>> getAllUser() {
		
		List<User> user = new ArrayList<User>();
		
		userService.getAllUser().forEach(user :: add);
		
		if(user.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<User>>(user,HttpStatus.OK);
		
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		
		Optional<User> user = userService.findById(id);
		
		if(user.isPresent()) {
			return new ResponseEntity<User>(user.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userdata) {
		
		Optional<User> finduser = userService.findById(id);
		
		if(finduser.isPresent()) {
			User user = finduser.get();
			user.setUsername(userdata.getUsername());
			user.setContactNo(userdata.getContactNo());
			user.setEmail(userdata.getEmail());
			return new ResponseEntity<User>(userService.saveuser(user),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

	}
	
	@PutMapping("/user/forgotpassword/{id}")
	public ResponseEntity<?> updateUserPassword(@PathVariable String id, @RequestBody User userdata) {
		
		if(userService.existsByEmail(id)) {
			Optional<User> finduser = userService.findByEmail(id);
			
			if(finduser.isPresent()) {
				User user = finduser.get();
				user.setPassword(encoder.encode(userdata.getPassword()));
				return new ResponseEntity<User>(userService.saveuser(user),HttpStatus.OK);
			}
			else {
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
		}
		else {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : USERNAME NOT AVAILABLE !..."));
		}
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		userService.deleteById(id);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	@DeleteMapping("/user/{userId}/role/{roleId}")
	public ResponseEntity<User> removeRoleFromUser(@PathVariable Long userId, @PathVariable int roleId) {
		
		Optional<User> user = userService.findById(userId);
		Optional<Role> role = roleService.findById(roleId);
		if(user.isPresent() && role.isPresent()) {
			user.get().getRoles().remove(role.get());
			userService.saveuser(user.get());
			return ResponseEntity.ok(user.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/usersrole/{id}")
	public ResponseEntity<User> updateUserRoles(@PathVariable Long id, @RequestBody User updateUser) {
		
		User user = userService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User"+id));
		
		user.setUsername(updateUser.getUsername());
		user.setContactNo(updateUser.getContactNo());
		user.setEmail(updateUser.getEmail());
		
		
		for(Role role : updateUser.getRoles()) {
			
			Optional<Role> existingRole = roleService.findByRole(role.getRole());
			if(!existingRole.isPresent()) {
				throw new ResourceNotFoundException("Role"+ role.getRole());
			}
			if(!user.getRoles().contains(existingRole.get())) {
				user.getRoles().add(existingRole.get());
			}
		}
		
		User updated = userService.saveuser(user);
		return ResponseEntity.ok(updated);
	}
	
}
