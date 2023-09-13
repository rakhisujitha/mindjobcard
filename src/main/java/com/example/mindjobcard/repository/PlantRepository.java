package com.example.mindjobcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mindjobcard.model.Plant;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long>{

}
