package org.springboot.rbacsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestController.class));
	}
	
	@Override
	public void configureApiVersioning(ApiVersionConfigurer configurer) {
		configurer.usePathSegment(1)
		          .addSupportedVersions("1.0")
		          .setDefaultVersion("1.0");
	}
}
