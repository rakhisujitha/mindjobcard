package com.example.mindjobcard.service;

import java.util.List;
import java.util.Optional;

import com.example.mindjobcard.model.Product;

public interface ProductService {
	
	Product saveProduct(Product product);
	
	List<Product> getAllProduct();
	
	Optional<Product> findById(Long id);
	
	void deleteById(Long id);
	
	Boolean existsById(Long id);
	
	boolean existsByProductName(String name);

}
