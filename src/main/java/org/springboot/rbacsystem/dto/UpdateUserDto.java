package org.springboot.rbacsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserDto {
	
	private final String notBlankRegex = ".*\\S.*";
	
	@JsonProperty
	@Pattern(regexp = notBlankRegex, message = "Username must not be blank")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters long")
	private String username;
	
	@JsonProperty
	@Pattern(regexp = notBlankRegex, message = "Password must not be blank")
	@Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long")
	private String password;
	
	@JsonProperty(value = "full_name")
	@Pattern(regexp = notBlankRegex, message = "Full name must not be blank")
	@Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters long")
	private String fullName;
	
	@JsonProperty
	@Email(message = "Email should be valid")
	@Pattern(regexp = notBlankRegex, message = "Email must not be blank")
	@Size(max = 100, message = "Email must be at most 100 characters long")
	private String email;
	
	@JsonProperty(value = "user_role_ids")
	private List<Long> roleIds;
}
