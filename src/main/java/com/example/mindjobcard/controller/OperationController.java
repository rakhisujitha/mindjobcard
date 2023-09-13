package com.example.mindjobcard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.example.mindjobcard.model.Machine;
import com.example.mindjobcard.model.Operation;
import com.example.mindjobcard.model.Product;
import com.example.mindjobcard.service.MachineService;
import com.example.mindjobcard.service.OperationService;
import com.example.mindjobcard.service.ProductService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class OperationController {
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private MachineService machineService;
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/operation")
	public ResponseEntity<Operation> createOperation(@RequestBody Operation operation) {
		
		return ResponseEntity.ok(operationService.saveOperation(operation));
	}

	@GetMapping("/operation")
	public ResponseEntity<List<Operation>> getAllOperation() {
		
		List<Operation> operation = new ArrayList<Operation>();
		
		operationService.getAllOperation().forEach(operation::add);
		
		if(operation.isEmpty()) {
			return new ResponseEntity<List<Operation>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Operation>>(operation,HttpStatus.OK);
	}
	
	@GetMapping("/operation/{id}")
	public ResponseEntity<Operation> getOperationById(@PathVariable Long id) {
		
		Optional<Operation> operation = operationService.findById(id);
		
		if(operation.isPresent()) {
			return new ResponseEntity<Operation>(operation.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Operation>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/product/{id}/operation")
	public ResponseEntity<List<Operation>> getAllOperationsByProductId(@PathVariable Long id) {
		
		if(!productService.existsById(id) ) {
			throw new ResourceNotFoundException("Product not found");
		}
		
		List<Operation> operations = operationService.findOperationsByProductsId(id);
		return new ResponseEntity<List<Operation>>(operations,HttpStatus.OK);
	}
	
	@GetMapping("/products/{id}/operation")
	public ResponseEntity<List<Operation>> getAllOperationsByProductName(@PathVariable String id) {
		
		if(!productService.existsByProductName(id) ) {
			throw new ResourceNotFoundException("Product not found");
		}
		
		List<Operation> operations = operationService.findOperationsByProductsProductName(id);
		return new ResponseEntity<List<Operation>>(operations,HttpStatus.OK);
	}
	
	@PutMapping("/operation/{id}")
	public ResponseEntity<Operation> updateOperation(@PathVariable Long id, @RequestBody Operation updateOperation) {
		
		Optional<Operation> findOperation = operationService.findById(id);
		
		if(findOperation.isPresent()) {
			Operation operation = findOperation.get();
			operation.setOperationName(updateOperation.getOperationName());
			operation.setOperationDesc(updateOperation.getOperationDesc());
			return new ResponseEntity<Operation>(operationService.saveOperation(operation),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Operation>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/operation/{id}")
	public ResponseEntity<Operation> deleteOperation(@PathVariable Long id) {

		Operation operation = operationService.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("OPERATION NOT FOUND"));
		
		Set<Machine> machines = operation.getMachines();
		Set<Product> products = operation.getProducts();
		
		for(Machine machine : machines) {
			machine.getOperations().remove(operation);
			machineService.saveMachine(machine);
		}
		
		for(Product product : products) {
			product.getOperations().remove(operation);
			productService.saveProduct(product);
		}
		operationService.delete(operation);
		return ResponseEntity.noContent().build();
	}
	
}
