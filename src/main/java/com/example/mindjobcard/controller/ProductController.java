package com.example.mindjobcard.controller;

import java.util.ArrayList;
import java.util.HashSet;
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

import com.example.mindjobcard.dto.MessageResponse;
import com.example.mindjobcard.dto.ProductRequest;
import com.example.mindjobcard.exception.ResourceNotFoundException;
import com.example.mindjobcard.model.Operation;
import com.example.mindjobcard.model.Product;
import com.example.mindjobcard.service.OperationService;
import com.example.mindjobcard.service.ProductService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OperationService operationService;
	
	@PostMapping("/product")
	public ResponseEntity<?> saveProduct(@RequestBody ProductRequest productRequest) {
		
		Product product = new Product(productRequest.getProductName(), productRequest.getProductdesc());
		Set<Long> productOperations = productRequest.getOperation();
		Set<Operation> operations = new HashSet<>();
		if(productOperations == null) {
			System.out.println("OPERATIONS ARE NULL");
		}
		else {
			productOperations.forEach(opeartion -> {
				Operation findOperation = operationService.findById(opeartion).
						orElseThrow(() -> new RuntimeException("OPERTION ARE NULL"));
				operations.add(findOperation);
			});
			product.setOperations(operations);
			productService.saveProduct(product);
		}
		return ResponseEntity.ok(new MessageResponse("PRODUCT SAVED SUCCESSFULLY"));
	}

	@GetMapping("/product")
	public ResponseEntity<List<Product>> getAllProduct() {
		
		List<Product> products = new ArrayList<Product>();
		
		productService.getAllProduct().forEach(products::add);
		
		if(products.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		
		Optional<Product> product = productService.findById(id);
		
		if(product.isEmpty()) {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<Product>(product.get(),HttpStatus.OK);
		}
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updaetProduct) {
		
		Product product = productService.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Product not found"+id));
		
		product.setProductName(updaetProduct.getProductName());
		product.setProductdesc(updaetProduct.getProductdesc());
		
		for(Operation operation : updaetProduct.getOperations()) {
			Optional <Operation> existingOperation = operationService.findById(operation.getId());	
			if(!existingOperation.isPresent()) {
				throw new ResourceNotFoundException("Operation not found"+ operation.getId());
			}
			if(!product.getOperations().contains(existingOperation.get())) {
				product.getOperations().add(existingOperation.get());
			}
		}
		
		Product updated = productService.saveProduct(product);
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<Product> deleteById(@PathVariable Long id) {
		
		productService.deleteById(id);
		return new ResponseEntity<Product>(HttpStatus.OK);
	}
	
	@DeleteMapping("/product/{productId}/operation/{operationId}")
	public ResponseEntity<Product> removeOperationFromProduct(@PathVariable Long productId, @PathVariable Long operationId) {
		
		Optional<Product> product = productService.findById(productId);
		Optional<Operation> operation = operationService.findById(operationId);
		
		if(product.isPresent() && operation.isPresent()) {
			product.get().getOperations().remove(operation.get());
			productService.saveProduct(product.get());
			return ResponseEntity.ok(product.get());
		}
		else {
			return ResponseEntity.noContent().build();
		}
	}
}
