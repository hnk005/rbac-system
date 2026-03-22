package org.springboot.rbacsystem.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springboot.rbacsystem.dto.SystemApiResponseDto;
import org.springboot.rbacsystem.security.RequirePermission;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiConfig {
	private final RequestMappingHandlerMapping handlerMapping;
	
	@EventListener(ApplicationReadyEvent.class)
	@Bean
	List<SystemApiResponseDto> apiResponseDtos() {
		log.info("Bắt đầu thu thập thông tin API...");
		
		List<SystemApiResponseDto> apiList = new ArrayList<>();
		
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
		
		for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
			RequestMappingInfo mappingInfo = entry.getKey();
			HandlerMethod handlerMethod = entry.getValue();
			
			RequirePermission permission = handlerMethod.getMethodAnnotation(RequirePermission.class);
			
			if (permission != null) {
				HttpMethod httpMethod = mappingInfo.getMethodsCondition()
				                                   .getMethods()
				                                   .iterator()
				                                   .next()
				                                   .asHttpMethod();
				
				final StringBuilder url = new StringBuilder();
				if (mappingInfo.getPathPatternsCondition() != null) {
					url.append(mappingInfo.getPathPatternsCondition()
					                      .getPatternValues()
					                      .iterator()
					                      .next());
				}
				
				if (mappingInfo.getVersionCondition()
				               .getVersion() != null) {
					
					String prefixVersion = "{version}";
					int start = url.indexOf(prefixVersion);
					int end = url.indexOf(prefixVersion) + prefixVersion.length();
					
					url.replace(start, end, mappingInfo.getVersionCondition()
					                                   .getVersion());
				}
				
				
				apiList.add(SystemApiResponseDto.builder()
				                                .method(httpMethod.name())
				                                .url(url.toString())
				                                .resourceOwner(permission.owner()
				                                                         .getValue())
				                                .action(permission.action()
				                                                  .getValue())
				                                .build());
			}
		}
		
		log.info("Đã thu thập {} API có annotation @RequirePermission", apiList.size());
		
		return apiList;
	}
}
