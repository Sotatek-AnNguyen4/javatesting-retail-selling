package com.sotatek.rea.infrastructure.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseData<T> {

	public Integer code;
	
	public T data;
}
