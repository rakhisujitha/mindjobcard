package com.example.mindjobcard.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mindjobcard.model.Role;
import com.example.mindjobcard.repository.RoleRepository;
import com.example.mindjobcard.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Optional<Role> findByRole(String role) {
		return roleRepository.findByRole(role);
	}

	@Override
	public List<Role> getAllRole() {
		return roleRepository.findAll();
	}

	@Override
	public Optional<Role> findById(int id) {
		return roleRepository.findById(id);
	}

	@Override
	public Boolean existsById(int id) {
		return roleRepository.existsById(id);
	}

}
