package org.springboot.rbacsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
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
	
	@JsonProperty(index = 1)
	@Null(message = "ID must be null", groups = {onRegister.class})
	private Long id;
	
	@Null(message = "Username must be null", groups = {onRegister.class})
	private String username;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "Password must not be blank", groups = {onRegister.class})
	@Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long", groups = {
			onRegister.class})
	private String password;
	
	@JsonProperty(value = "full_name", required = true)
	@NotBlank(message = "Full name must not be blank", groups = {onRegister.class})
	@Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters long", groups =
			{onRegister.class})
	private String fullName;
	
	@JsonProperty(required = true)
	@NotBlank(message = "Full name must not be blank", groups = {onRegister.class})
	@Email(message = "Email should be valid", groups = {onRegister.class})
	@Size(max = 100, message = "Email must be at most 100 characters long", groups = {
			onRegister.class})
	private String email;
	
	@JsonProperty(value = "user_roles")
	@Null(message = "Roles must be null when creating a new user", groups = {onRegister.class})
	private List<RoleDto> roles;
	
	public interface onRegister {
	}
}
