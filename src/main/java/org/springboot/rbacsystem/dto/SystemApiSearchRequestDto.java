package org.springboot.rbacsystem.dto;

import lombok.Data;

@Data
public class SystemApiSearchRequestDto {
	private String url;
	private String method;
	private String resourceOwner;
	private String action;
}
