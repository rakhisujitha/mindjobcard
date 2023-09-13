package com.example.mindjobcard.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mindjobcard.model.Plant;
import com.example.mindjobcard.repository.PlantRepository;
import com.example.mindjobcard.service.PlantService;

@Service
public class PlantServiceImpl implements PlantService{

	@Autowired
	private PlantRepository plantRepository;
	
	@Override
	public Plant savePlant(Plant plant) {
		return plantRepository.save(plant);
	}

	@Override
	public List<Plant> getAllPlant() {
		return plantRepository.findAll();
	}

	@Override
	public Optional<Plant> findById(Long id) {
		return plantRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		plantRepository.deleteById(id);
	}

}
