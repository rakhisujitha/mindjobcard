package com.example.mindjobcard.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "batchorder")
public class BatchOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batchorder_id")
	private Long id ;
	
	@Column(name = "comapany_name")
	private String comapanyName;
	
	@Column(name = "company_person")
	private String companyPerson;
	
	@Column(name = "item")
	private String item;
	
	@Column(name = "raw_material")
	private String rawMaterial;
	
	@Column(name = " fin_size")
	private String finSize;
	
	@Column(name = "assembly")
	private String assembly;
	
	@Column(name = "purchase_qty")
	private int purchaseQty;
	
	@Column(name = "purchase_no")
	private String purchaseNo;
	
	@Column(name = "purchase_date")
	private Date purchaseDate;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "priority")
	private int priority;
	
	@Column(name = "production_qty")
	private int productionQty;
	
	@Column(name = "issue_date")
	private Date issueDate;
	
	@Column(name = "accurate_qty")
	private int accurateQty;
	
	@Column(name = "rejected_qty")
	private int rejectedQty;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComapanyName() {
		return comapanyName;
	}

	public void setComapanyName(String comapanyName) {
		this.comapanyName = comapanyName;
	}

	public String getCompanyPerson() {
		return companyPerson;
	}

	public void setCompanyPerson(String companyPerson) {
		this.companyPerson = companyPerson;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getRawMaterial() {
		return rawMaterial;
	}

	public void setRawMaterial(String rawMaterial) {
		this.rawMaterial = rawMaterial;
	}

	public String getFinSize() {
		return finSize;
	}

	public void setFinSize(String finSize) {
		this.finSize = finSize;
	}

	public String getAssembly() {
		return assembly;
	}

	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}

	public int getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(int purchaseQty) {
		this.purchaseQty = purchaseQty;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getProductionQty() {
		return productionQty;
	}

	public void setProductionQty(int productionQty) {
		this.productionQty = productionQty;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public int getAccurateQty() {
		return accurateQty;
	}

	public void setAccurateQty(int accurateQty) {
		this.accurateQty = accurateQty;
	}

	public int getRejectedQty() {
		return rejectedQty;
	}

	public void setRejectedQty(int rejectedQty) {
		this.rejectedQty = rejectedQty;
	}
	
}
