package com.example.mindjobcard.service;

import java.util.List;
import java.util.Optional;

import com.example.mindjobcard.model.BatchOrder;

public interface BatchOrderService {
	
	BatchOrder saveBatchOrder(BatchOrder batchOrder);
	
	List<BatchOrder> getAllBatchOrder();
	
	Optional<BatchOrder> findById(Long id);
	
	void deleteById(Long id);
	
	Boolean existsById(Long id);
	
	void delete(BatchOrder batchOrder);
}
