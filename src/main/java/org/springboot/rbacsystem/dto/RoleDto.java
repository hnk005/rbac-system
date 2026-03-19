package org.springboot.rbacsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {
	
	@JsonProperty(index = 1)
	private Long id;
	
	@JsonProperty
	private String name;
	
	@JsonProperty(value = "description")
	private String des;
	
	@JsonProperty(value = "role_permissions")
	private List<PermissionDto> permissions;
}
