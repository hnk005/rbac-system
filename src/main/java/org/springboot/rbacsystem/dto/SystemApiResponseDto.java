package org.springboot.rbacsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemApiResponseDto {
	private String url;
	private String method;
	private String resourceOwner;
	private String action;
}
