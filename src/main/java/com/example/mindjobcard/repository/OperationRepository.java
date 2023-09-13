package com.example.mindjobcard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mindjobcard.model.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>{

	List<Operation> findOperationsByProductsId(Long id);
	
	List<Operation> findOperationsByProductsProductName(String name);
	
	boolean existsByOperationName(String name);
}
