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

import com.example.mindjobcard.dto.MessageResponse;
import com.example.mindjobcard.dto.PlantRequest;
import com.example.mindjobcard.exception.ResourceNotFoundException;
import com.example.mindjobcard.model.Machine;
import com.example.mindjobcard.model.Plant;
import com.example.mindjobcard.service.MachineService;
import com.example.mindjobcard.service.PlantService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class PlantController {
	
	@Autowired
	private PlantService plantService;
	
	@Autowired
	private MachineService machineService;
	
	@PostMapping("/plant")
	public ResponseEntity<?> savePlant(@RequestBody PlantRequest plantRequest) {
		
		Plant plant = new Plant(plantRequest.getPlantName(),plantRequest.getLocation());
		Set<Long> plantMachines = plantRequest.getMachine();
		Set<Machine> machines = new HashSet<>();
		if(plantMachines == null) {
			System.out.println("MACHINES ARE NULL");
		}
		else {
			plantMachines.forEach(machine ->{
				Machine findmachine = machineService.findById(machine).
						orElseThrow(() -> new RuntimeException("Machine Not found"));
				machines.add(findmachine);
			});
			plant.setMachines(machines);
			plantService.savePlant(plant);
		}
		return ResponseEntity.ok(new MessageResponse("PLANT SAVED SUCCESSFULLY"));
	}
	
	@GetMapping("/plant")
	public ResponseEntity<List<Plant>> getAllPlant() {
		
		List<Plant> plant = new ArrayList<Plant>();
		
		plantService.getAllPlant().forEach(plant::add);
		
		if(plant.isEmpty()) {
			return new ResponseEntity<List<Plant>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Plant>>(plant, HttpStatus.OK);
	}
	
	@GetMapping("/plant/{id}")
	public ResponseEntity<Plant> getPlantById(@PathVariable Long id) {
		
		Optional<Plant> plant = plantService.findById(id);
		
		if(plant.isEmpty()) {
			return new ResponseEntity<Plant>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<Plant>(plant.get(),HttpStatus.OK);
		}
	}
	
	@PutMapping("/plant/{id}")
	public ResponseEntity<Plant> updatePlant(@PathVariable Long id, @RequestBody Plant updatePlant) {
		
		Plant plant = plantService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Plant"+id));
		
		plant.setPlantName(updatePlant.getPlantName());
		plant.setLocation(updatePlant.getLocation());
		
		for(Machine machine : updatePlant.getMachines()) {
			
			Optional<Machine> existingMachine = machineService.findById(machine.getId());
			if(!existingMachine.isPresent()) {
				throw new ResourceNotFoundException("Machine nor found"+machine.getId());
			}
			if(!plant.getMachines().contains(existingMachine.get())) {
				plant.getMachines().add(existingMachine.get());
			}
		}
		Plant update = plantService.savePlant(plant);
		return ResponseEntity.ok(update);
	}

	@DeleteMapping("plant/{id}")
	public ResponseEntity<Plant> deleteById(@PathVariable Long id) {
		plantService.deleteById(id);
		return new ResponseEntity<Plant>(HttpStatus.OK);
	}
	
	@DeleteMapping("/plant/{plantId}/machine/{machineId}")
	public ResponseEntity<Plant> removeOperationFromProduct(@PathVariable Long plantId, @PathVariable Long machineId) {
		
		Optional<Plant> plant = plantService.findById(plantId);
		Optional<Machine> machine = machineService.findById(machineId);
		
		if(plant.isPresent() && machine.isPresent()) {
			plant.get().getMachines().remove(machine.get());
			plantService.savePlant(plant.get());
			return ResponseEntity.ok(plant.get());
		}
		else {
			return ResponseEntity.noContent().build();
		}
	}
	
}
