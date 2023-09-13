package com.example.mindjobcard.service;

import java.util.List;
import java.util.Optional;

import com.example.mindjobcard.model.Operation;

public interface OperationService {
	
	Operation saveOperation(Operation operation);
	
	List<Operation> getAllOperation();
	
	Optional<Operation> findById(Long id);
	
	void deleteById(Long id);
	
	void delete(Operation operation);

	List<Operation> findOperationsByProductsId(Long id);
	
	List<Operation> findOperationsByProductsProductName(String name);
	
	boolean existsByOperationName(String name);

}
