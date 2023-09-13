package com.example.mindjobcard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mindjobcard.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	List<User> findUsersByRolesId(int id);

}
