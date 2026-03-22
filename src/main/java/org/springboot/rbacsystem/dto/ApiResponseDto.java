package org.springboot.rbacsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDto {
	private String url;
	private String method;
	private String resourceOwner;
	private String action;
}
