package com.example.mindjobcard.service;

import java.util.List;
import java.util.Optional;

import com.example.mindjobcard.model.Machine;

public interface MachineService {
	
	Machine saveMachine(Machine machine);
	
	List<Machine> getAllMachine();
	
	Optional<Machine> findById(Long id);
	
	void deleteById(Long id);
	
	void delete(Machine machine);
	
	List<Machine> findMachinesByOperationsOperationName(String name);
}
