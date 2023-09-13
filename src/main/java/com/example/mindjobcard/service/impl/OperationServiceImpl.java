package com.example.mindjobcard.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mindjobcard.model.Operation;
import com.example.mindjobcard.repository.OperationRepository;
import com.example.mindjobcard.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService{

	@Autowired
	OperationRepository operationRepository;
	
	@Override
	public Operation saveOperation(Operation operation) {
		return operationRepository.save(operation);
	}

	@Override
	public List<Operation> getAllOperation() {
		return operationRepository.findAll();
	}

	@Override
	public Optional<Operation> findById(Long id) {
		return operationRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		operationRepository.deleteById(id);
	}

	@Override
	public void delete(Operation operation) {
		operationRepository.delete(operation);
	}

	@Override
	public List<Operation> findOperationsByProductsId(Long id) {
		return operationRepository.findOperationsByProductsId(id);
	}

	@Override
	public List<Operation> findOperationsByProductsProductName(String name) {
		return operationRepository.findOperationsByProductsProductName(name);
	}

	@Override
	public boolean existsByOperationName(String name) {
		return operationRepository.existsByOperationName(name);
	}

}
