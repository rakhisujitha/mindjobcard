package com.example.mindjobcard.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mindjobcard.model.BatchOrder;
import com.example.mindjobcard.repository.BatchOrderRepository;
import com.example.mindjobcard.service.BatchOrderService;

@Service
public class BatchOrderServiceImpl implements BatchOrderService{

	@Autowired
	private BatchOrderRepository batchOrderRepository;
	
	@Override
	public BatchOrder saveBatchOrder(BatchOrder batchOrder) {
		return batchOrderRepository.save(batchOrder);
	}

	@Override
	public List<BatchOrder> getAllBatchOrder() {
		return batchOrderRepository.findAll();
	}

	@Override
	public Optional<BatchOrder> findById(Long id) {
		return batchOrderRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		batchOrderRepository.deleteById(id);
	}

	@Override
	public Boolean existsById(Long id) {
		return batchOrderRepository.existsById(id);
	}

	@Override
	public void delete(BatchOrder batchOrder) {
		batchOrderRepository.delete(batchOrder);
	}

}
