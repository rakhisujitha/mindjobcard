package com.example.mindjobcard.dto;

import java.util.Set;

public class ProductRequest {

	private String productName;
	private String productdesc;
	private Set<Long> operation;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductdesc() {
		return productdesc;
	}
	public void setProductdesc(String productdesc) {
		this.productdesc = productdesc;
	}
	public Set<Long> getOperation() {
		return operation;
	}
	public void setOperation(Set<Long> operation) {
		this.operation = operation;
	}
	
	
}
