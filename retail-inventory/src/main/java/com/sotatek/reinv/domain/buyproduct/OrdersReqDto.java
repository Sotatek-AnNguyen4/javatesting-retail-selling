package com.sotatek.reinv.domain.buyproduct;

import com.sotatek.reinv.infrastructure.model.Product;

import lombok.Data;

public class OrdersReqDto {

    public Long productId;
	
	public Integer quantity;
	
	public Product product;
	
}
