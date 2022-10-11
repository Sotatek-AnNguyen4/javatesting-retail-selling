package com.sotatek.rea.domain.receiveamount;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ReceiveAmountReqDto {

	public Long retailId;
	
	public Long amount;
	
	public Long orderId;
}
