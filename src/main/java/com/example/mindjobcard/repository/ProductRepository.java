package com.example.mindjobcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mindjobcard.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	boolean existsByProductName(String name);

}
