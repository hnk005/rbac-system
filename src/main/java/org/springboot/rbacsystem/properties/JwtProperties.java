package org.springboot.rbacsystem.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
@Validated
public class JwtProperties {

	private String secretKey;
	
	private long expirationTime; // in milliseconds
}
