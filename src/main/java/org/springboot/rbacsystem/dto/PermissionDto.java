package org.springboot.rbacsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@AllArgsConstructor
public class PermissionDto {
	
	@JsonProperty(index = 1)
	private Long id;
	
	@JsonProperty(required = true)
	@NotBlank(message = "Permission name must not be blank")
	@Max(50)
	private String name;
	
	@JsonProperty(value = "description")
	@Max(255)
	private String des;
}
