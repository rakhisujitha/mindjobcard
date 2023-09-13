package com.example.mindjobcard.service;

import java.util.List;
import java.util.Optional;

import com.example.mindjobcard.model.Role;


public interface RoleService {
	
	Optional<Role> findByRole(String role);
	
	Optional<Role> findById(int id);
	
	List<Role> getAllRole();
	
	Boolean existsById(int id);
}
