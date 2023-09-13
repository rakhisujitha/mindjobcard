package com.example.mindjobcard.dto;

import java.util.Set;

public class PlantRequest {
	
	private String plantName;
	private String location;
	private Set<Long> machine;
	
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Set<Long> getMachine() {
		return machine;
	}
	public void setMachine(Set<Long> machine) {
		this.machine = machine;
	}

}
