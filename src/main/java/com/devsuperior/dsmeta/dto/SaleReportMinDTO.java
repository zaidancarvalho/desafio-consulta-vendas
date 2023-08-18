package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

public class SaleReportMinDTO {

	private Long id;
	private LocalDate date;
	private Double amount;
	private String sellerName;
	
	public SaleReportMinDTO(Long id, LocalDate date, Double amount, String sellerName) {
		super();
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.sellerName = sellerName;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public Double getAmount() {
		return amount;
	}

	public String getSellerName() {
		return sellerName;
	}
}
