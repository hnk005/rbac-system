package org.springboot.rbacsystem.service;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.ApiRequestDto;
import org.springboot.rbacsystem.dto.ApiResponseDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SystemService {
	
	private final List<ApiResponseDto> apiResponseDtos;
	
	public List<ApiResponseDto> getApiAll() {
		return apiResponseDtos.stream()
		                      .toList();
	}
	
	public List<ApiResponseDto> searchApi(ApiRequestDto dto) {
		
		Set<String> urlSet = new HashSet<>();
		
		return apiResponseDtos.stream()
		                      .filter(api -> {
			                      boolean matches = true;
			                      
			                      if (dto.getUrl() != null) {
				                      matches &= api.getUrl()
				                                    .contains(dto.getUrl());
			                      }
			                      if (dto.getMethod() != null) {
				                      matches &= api.getMethod()
				                                    .equalsIgnoreCase(dto.getMethod());
			                      }
			                      
			                      if (dto.getResourceOwner() != null) {
				                      matches &= api.getResourceOwner()
				                                    .equalsIgnoreCase(dto.getResourceOwner());
			                      }
			                      if (dto.getAction() != null) {
				                      matches &= api.getAction()
				                                    .equalsIgnoreCase(dto.getAction());
			                      }
			                      return matches;
		                      })
		                      .filter(api -> urlSet.add(api.getUrl()))
		                      .toList();
	}
}
