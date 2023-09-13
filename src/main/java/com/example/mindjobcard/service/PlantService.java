package com.example.mindjobcard.service;

import java.util.List;
import java.util.Optional;

import com.example.mindjobcard.model.Plant;

public interface PlantService {
	
	Plant savePlant(Plant plant);
	
	List<Plant> getAllPlant();
	
	Optional<Plant> findById(Long id);
	
	void deleteById(Long id);

}
