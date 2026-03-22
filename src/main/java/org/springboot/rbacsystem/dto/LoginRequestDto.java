package org.springboot.rbacsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequestDto {
	
	@JsonProperty
	@NotBlank(message = "Username must not be blank")
	private String username;
	
	@JsonProperty
	@NotBlank(message = "Username must not be blank")
	private String password;
}
