package com.sotatek.order.domain.createorder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderReqDto {

	public Long productId;
	
	public Long price;
	
	public Integer quantity;
}
