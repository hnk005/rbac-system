package org.springboot.rbacsystem.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceArgumentNotValidException extends RuntimeException {
	private String field;
	
	public ServiceArgumentNotValidException(String field, String message) {
		super(message);
		this.field = field;
	}
}
