package org.springboot.rbacsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionDto {
	
	@JsonProperty(index = 1)
	private Long id;
	
	@JsonProperty
	private String name;
	
	@JsonProperty(value = "description")
	private String des;
}
