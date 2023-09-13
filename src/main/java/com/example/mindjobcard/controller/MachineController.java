package com.example.mindjobcard.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mindjobcard.dto.MachineRequest;
import com.example.mindjobcard.dto.MessageResponse;
import com.example.mindjobcard.exception.ResourceNotFoundException;
import com.example.mindjobcard.model.Machine;
import com.example.mindjobcard.model.Operation;
import com.example.mindjobcard.model.Plant;
import com.example.mindjobcard.service.MachineService;
import com.example.mindjobcard.service.OperationService;
import com.example.mindjobcard.service.PlantService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class MachineController {
	
	@Autowired 
	private MachineService machineService;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private PlantService plantService;
	
	@PostMapping("/machine")
	public ResponseEntity<?> saveMachine(@RequestBody MachineRequest machineRequest) {
		
		Machine machine = new Machine(machineRequest.getMachineName(),machineRequest.getMachineDesc());
		Set<Long> machineOpertions = machineRequest.getOperation();
		Set<Operation> operations = new HashSet<>();
		if(machineOpertions == null) {
			System.out.print("OPERATION ARE NULL");
		}
		else {
			machineOpertions.forEach(operation -> {
				Operation findOperation = operationService.findById(operation).
						orElseThrow(() -> new RuntimeException("OPERTION NOT FOUND"));
				operations.add(findOperation);
			});
			machine.setOperations(operations);
			machineService.saveMachine(machine);
		}
		return ResponseEntity.ok(new MessageResponse("MACHINE SAVED SUCCESSFULLY"));
	}
	
	@GetMapping("/machine")
	public ResponseEntity<List<Machine>> getAllMachine() {
		
		List<Machine> machine= new ArrayList<Machine>();
	
		machineService.getAllMachine().forEach(machine::add);
		
		if(machine.isEmpty()) {
			return new ResponseEntity<List<Machine>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Machine>>(machine,HttpStatus.OK);
	}
	
	@GetMapping("/machine/{id}")
	public ResponseEntity<Machine> getMachineById(@PathVariable Long id) {
		
		Optional<Machine> machine = machineService.findById(id);
		
		if(machine.isPresent()) {
			return new ResponseEntity<Machine>(machine.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Machine>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/operation/{id}/machine")
	public ResponseEntity<List<Machine>> getAllMachinesByOperationName(@PathVariable String id) {
	
		if(!operationService.existsByOperationName(id)) {
			throw new ResourceNotFoundException("Operation not found");
		}
		
		List<Machine> machines = machineService.findMachinesByOperationsOperationName(id);
		return new ResponseEntity<List<Machine>>(machines, HttpStatus.OK);
	}
	
	
	
	@PutMapping("/machine/{id}")
	public ResponseEntity<Machine> updateMachine(@PathVariable Long id, @RequestBody Machine updateMachine) {
		
		Machine machine = machineService.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Machine"+id));
		
		machine.setMachineName(updateMachine.getMachineName());
		machine.setMachineDesc(updateMachine.getMachineDesc());
		
		for(Operation operation : updateMachine.getOperations()) {
			
			Optional<Operation> existingOperation = operationService.findById(operation.getId());
			if(!existingOperation.isPresent()) {
				throw new ResourceNotFoundException("Operation"+operation.getId());
			}
			if(!machine.getOperations().contains(existingOperation.get())) {
				machine.getOperations().add(existingOperation.get());
			}
		}
		Machine updated = machineService.saveMachine(machine);
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/machine/{id}")
	public ResponseEntity<Machine> deleteMachine(@PathVariable Long id) {
		
		Machine machine = machineService.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Machine not found"));
		
		Set<Plant> plants = machine.getPlants();
		
		for(Plant plant : plants) {
			plant.getMachines().remove(machine);
			plantService.savePlant(plant);
		}
		machineService.delete(machine);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/machine/{machineId}/operation/{operationId}")
	public ResponseEntity<Machine> removeOperationFromProduct(@PathVariable Long machineId, @PathVariable Long operationId) {
		
		Optional<Machine> machine = machineService.findById(machineId);
		Optional<Operation> operation = operationService.findById(operationId);
		
		if(machine.isPresent() && operation.isPresent()) {
			machine.get().getOperations().remove(operation.get());
			machineService.saveMachine(machine.get());
			return ResponseEntity.ok(machine.get());
		}
		else {
			return ResponseEntity.noContent().build();
		}
	}
}
