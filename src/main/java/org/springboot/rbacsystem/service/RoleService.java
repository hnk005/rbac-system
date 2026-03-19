package org.springboot.rbacsystem.service;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.entity.RoleEntity;
import org.springboot.rbacsystem.mapper.role.RoleMapper;
import org.springboot.rbacsystem.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository repository;
	private final RoleMapper mapper;
	
	public List<RoleDto> findAllById(List<Long> ids) {
		
		List<RoleEntity> roleEntity = repository.findAllById(ids);
		
		return roleEntity.stream()
		                 .map(mapper::toDto)
		                 .collect(Collectors.toList());
	}
}
