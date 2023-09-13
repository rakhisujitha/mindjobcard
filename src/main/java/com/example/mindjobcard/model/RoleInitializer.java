package com.example.mindjobcard.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.mindjobcard.repository.RoleRepository;

@Component
public class RoleInitializer implements ApplicationRunner{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		if(roleRepository.count() == 0) {
			
			Role adminRole = new Role();
			adminRole.setRole("ADMIN");
			roleRepository.save(adminRole);
			
			Role managerRole = new Role();
			managerRole.setRole("MANAGER");
			roleRepository.save(managerRole);
			
			Role userRole = new Role();
			userRole.setRole("USER");
			roleRepository.save(userRole);
			
		}
	}

}
