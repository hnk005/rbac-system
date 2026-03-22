package org.springboot.rbacsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@FieldNameConstants
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateRoleDto {
	@JsonProperty
	@NotBlank(message = "Role name must not be blank")
	@Size(min = 2, max = 50, message = "Role name must be between 2 and 50 characters long")
	private String name;
	
	@JsonProperty(value = "description")
	@Size(max = 200, message = "Description must be at most 200 characters long")
	private String des;
	
	@JsonProperty(value = "role_permissions")
	private List<Long> permissionIds;
}
