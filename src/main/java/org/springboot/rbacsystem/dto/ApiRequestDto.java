package org.springboot.rbacsystem.dto;

import lombok.Data;

@Data
public class ApiRequestDto {
	private String url;
	private String method;
	private String resourceOwner;
	private String action;
}
