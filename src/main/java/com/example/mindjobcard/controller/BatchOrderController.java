package com.example.mindjobcard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mindjobcard.exception.ResourceNotFoundException;
import com.example.mindjobcard.model.BatchOrder;
import com.example.mindjobcard.model.TaskList;
import com.example.mindjobcard.service.BatchOrderService;
import com.example.mindjobcard.service.TaskListService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class BatchOrderController {
	
	@Autowired
	private BatchOrderService batchOrderService;
	
	@Autowired
	private TaskListService taskListService;
	
	@PostMapping("/batchorder")
	public ResponseEntity<BatchOrder> saveBatchOrder(@RequestBody BatchOrder batchOrder) {
		return ResponseEntity.ok(batchOrderService.saveBatchOrder(batchOrder));
	}
	
	@GetMapping("/batchorder")
	public ResponseEntity<List<BatchOrder>> getAllBatchOrder() {
		
		List<BatchOrder> batchOrders = new ArrayList<BatchOrder>();
		
		batchOrderService.getAllBatchOrder().forEach(batchOrders::add);
		
		if(batchOrders.isEmpty()) {
			return new ResponseEntity<List<BatchOrder>>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<BatchOrder>>(batchOrders,HttpStatus.OK);
		}
	}
	
	@GetMapping("/batchorder/{id}")
	public ResponseEntity<BatchOrder> getBatchOrderById(@PathVariable Long id) {
		
		Optional<BatchOrder> batchorder = batchOrderService.findById(id);
		
		if(batchorder.isEmpty()) {
			return new ResponseEntity<BatchOrder>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<BatchOrder>(batchorder.get(),HttpStatus.OK);
		}
	}
	
	@PutMapping("/batchorder/{id}")
	public ResponseEntity<BatchOrder> updateBatchOrder(@PathVariable Long id, @RequestBody BatchOrder updateBatchOrder) {
		
		Optional<BatchOrder> findBatchOrder = batchOrderService.findById(id);
		
		if(findBatchOrder.isPresent()) {
			BatchOrder batchOrder = findBatchOrder.get();
			batchOrder.setComapanyName(updateBatchOrder.getComapanyName());
			batchOrder.setCompanyPerson(updateBatchOrder.getCompanyPerson());
			batchOrder.setItem(updateBatchOrder.getItem());
			batchOrder.setRawMaterial(updateBatchOrder.getRawMaterial());
			batchOrder.setFinSize(updateBatchOrder.getFinSize());
			batchOrder.setAssembly(updateBatchOrder.getAssembly());
			batchOrder.setPurchaseQty(updateBatchOrder.getPurchaseQty());
			batchOrder.setPurchaseNo(updateBatchOrder.getPurchaseNo());
			batchOrder.setPurchaseDate(updateBatchOrder.getPurchaseDate());
			batchOrder.setStartDate(updateBatchOrder.getStartDate());
			batchOrder.setPriority(updateBatchOrder.getPriority());
			batchOrder.setProductionQty(updateBatchOrder.getProductionQty());
			batchOrder.setIssueDate(updateBatchOrder.getIssueDate());
			batchOrder.setAccurateQty(updateBatchOrder.getAccurateQty());
			batchOrder.setRejectedQty(updateBatchOrder.getRejectedQty());
			return new ResponseEntity<BatchOrder>(batchOrderService.saveBatchOrder(batchOrder),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<BatchOrder>(HttpStatus.NOT_FOUND);
		}
	}
	

	@DeleteMapping("/batchorder/{id}")
	public ResponseEntity<BatchOrder> deleteBatchOrder(@PathVariable Long id) {
		
		BatchOrder batchOrder = batchOrderService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("BATCHORDER NOT FOUND"));
		
		List<TaskList> taskLists = taskListService.findByBatchOrderId(id);
		for(TaskList taskList : taskLists) {
			taskListService.deleteById(taskList.getId());
		}
		
		batchOrderService.delete(batchOrder);
		return new ResponseEntity<BatchOrder>(HttpStatus.OK);
	}
}
