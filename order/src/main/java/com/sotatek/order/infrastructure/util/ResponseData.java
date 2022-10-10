package com.sotatek.order.infrastructure.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseData<T> {

	public Integer code;
	
	public T data;
}
