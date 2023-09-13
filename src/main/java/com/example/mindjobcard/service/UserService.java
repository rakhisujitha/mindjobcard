package com.example.mindjobcard.service;

import java.util.List;
import java.util.Optional;

import com.example.mindjobcard.model.User;

public interface UserService {
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
		
	User saveuser(User user);
	
	List<User> getAllUser();
	
	void deleteById(Long id);
	
	Optional<User> findById(Long id);
	
	List<User> findUsersByRolesId(int id);

}
