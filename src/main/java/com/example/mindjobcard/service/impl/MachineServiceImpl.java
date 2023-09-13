package com.example.mindjobcard.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mindjobcard.model.Machine;
import com.example.mindjobcard.repository.MachineRepository;
import com.example.mindjobcard.service.MachineService;

@Service
public class MachineServiceImpl implements MachineService{

	@Autowired
	private MachineRepository machineRepository;
	
	@Override
	public Machine saveMachine(Machine machine) {
		return machineRepository.save(machine);
	}

	@Override
	public List<Machine> getAllMachine() {
		return machineRepository.findAll();
	}

	@Override
	public Optional<Machine> findById(Long id) {
		return machineRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		machineRepository.deleteById(id);
	}

	@Override
	public void delete(Machine machine) {
		machineRepository.delete(machine);
	}

	@Override
	public List<Machine> findMachinesByOperationsOperationName(String name) {
		return machineRepository.findMachinesByOperationsOperationName(name);
	}

}
