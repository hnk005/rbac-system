package org.springboot.rbacsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY, index = 1)
	private Long id;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String username;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "Password must not be blank")
	@Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long")
	private String password;
	
	@JsonProperty(value = "full_name", required = true)
	@NotBlank(message = "Full name must not be blank")
	@Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters long")
	private String fullName;
	
	@JsonProperty(required = true)
	@NotBlank(message = "Full name must not be blank")
	@Email(message = "Email should be valid")
	@Size(max = 100, message = "Email must be at most 100 characters long")
	private String email;
	
	@JsonProperty(value = "user_roles")
	private List<RoleDto> roles;
}
