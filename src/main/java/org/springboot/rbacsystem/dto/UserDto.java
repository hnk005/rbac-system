package org.springboot.rbacsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
	
	@JsonProperty(index = 1)
	private Long id;
	
	@JsonProperty
	private String username;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@JsonProperty(value = "full_name")
	private String fullName;
	
	@JsonProperty
	private String email;
	
	@JsonProperty(value = "user_roles")
	private List<UserRolesDto> roles;
}
