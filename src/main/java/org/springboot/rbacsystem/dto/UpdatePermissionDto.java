package org.springboot.rbacsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePermissionDto {
	private final String notBlankRegex = ".*\\S.*";
	
	@JsonProperty
	@Pattern(regexp = notBlankRegex, message = "Name must not be blank")
	@Size(min = 2, max = 50, message = "Permission name must be between 2 and 50 characters long")
	private String name;
	
	@JsonProperty(value = "description")
	@Size(max = 200, message = "Permission description must be at most 200 characters long")
	private String des;
}

