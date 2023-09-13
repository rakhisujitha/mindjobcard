package com.example.mindjobcard.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "plant")
public class Plant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plant_id")
	private Long id;
	
	@Column(name = "plant_name")
	private String plantName;
	
	@Column(name = "location")
	private String location;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "plant_machine",
	joinColumns = {@JoinColumn(name ="plant_id")},
	inverseJoinColumns = {@JoinColumn(name = "machine_id")})
	private Set<Machine> machines = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Set<Machine> getMachines() {
		return machines;
	}

	public void setMachines(Set<Machine> machines) {
		this.machines = machines;
	}

	public Plant(String plantName, String location) {
		super();
		this.plantName = plantName;
		this.location = location;
	}

	public Plant() {
		super();
	}
	
}
