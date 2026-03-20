package org.springboot.rbacsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePermissionDto {
	
	@JsonProperty
	@NotBlank(message = "Permission name must not be blank")
	@Size(min = 2, max = 50, message = "Permission name must be between 2 and 50 characters long")
	private String name;
	
	@JsonProperty(value = "description")
	@Size(max = 200, message = "Permission description must be at most 200 characters long")
	private String des;
}

