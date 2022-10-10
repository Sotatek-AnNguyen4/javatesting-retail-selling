package com.sotatek.order.domain.settlement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public interface SettlementResDto {
	
	Long getProductId();
	
	Long getAmount(); 
	
	Long getQuantity(); 
	
}
