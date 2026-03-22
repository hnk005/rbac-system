package org.springboot.rbacsystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("RBAC System API")
						.version("1.0.0")
						.description("Tài liệu API cho hệ thống quản lý phân quyền (Role-Based Access Control).")
						.contact(new Contact()
								.name("HNK")
								.email("huynhnamkha512020@gmeil.com")
								.url("https://github.com/hnk005"))
						.license(new License()
								.name("Apache 2.0")
								.url("https://springdoc.org"))
				)
				.components(new Components()
						.addSecuritySchemes("bearerAuth", new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")));
	}
	
	
}
