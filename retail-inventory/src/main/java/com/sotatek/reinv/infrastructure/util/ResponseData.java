package com.sotatek.reinv.infrastructure.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseData<T> {

	public Integer code;
	
	public T data;

	public ResponseData ifFailure(Class<Error> class1, Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}
