package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.repositories.SaleSummaryProjection;

public class SaleSummaryMinDTO {

	private String sellerName;
	private Double amount;

	
	public SaleSummaryMinDTO(SaleSummaryProjection projection) {
		this.sellerName = projection.getSellerName();
		this.amount = projection.getTotal();
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getAmount() {
		return amount;
	}

}
