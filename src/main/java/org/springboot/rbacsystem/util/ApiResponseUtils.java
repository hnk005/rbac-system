package org.springboot.rbacsystem.util;

import org.springboot.rbacsystem.dto.ApiResponseDto;

import java.time.LocalDateTime;

public class ApiResponseUtils {
	
	static public <T> ApiResponseDto<T> success(T data, String message) {
		return ApiResponseDto.<T>builder()
		                     .status(200)
		                     .message(message)
		                     .data(data)
		                     .timestamp(LocalDateTime.now())
		                     .build();
	}
}
