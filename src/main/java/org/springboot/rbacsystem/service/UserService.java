package org.springboot.rbacsystem.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.SQLGrammarException;
import org.springboot.rbacsystem.dto.CreateUserDto;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.entity.UserEntity;
import org.springboot.rbacsystem.mapper.role.RoleMapper;
import org.springboot.rbacsystem.mapper.user.UserMapper;
import org.springboot.rbacsystem.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final RoleService roleService;
	private final UserRepository repository;
	private final BCryptPasswordEncoder encoder;
	private final UserMapper mapper;
	private final RoleMapper roleMapper;
	
	public String generateUniqueUsername(String email) {
		String baseUsername = email.split("@")[0];
		String generatedUsername;
		boolean isUnique = false;
		int attempts = 0;
		
		do {
			// Append a random number (e.g., 5 digits)
			generatedUsername = baseUsername + (int) (Math.random() * 90000) + 10000;
			// Check if it exists in the database
			if (repository.findByUsername(generatedUsername) == null) {
				isUnique = true;
			}
			// Add a safety break condition for infinite loops
			if (attempts++ > 10) throw new RuntimeException("Could not generate unique username");
		} while (!isUnique);
		
		return generatedUsername;
	}
	
	@Transactional
	public String create(CreateUserDto dto) throws IllegalArgumentException, SQLGrammarException {
		
		if (dto == null) {
			throw new IllegalArgumentException("Dto cannot be null");
		}
		
		UserEntity existEmail = repository.findByEmail(dto.getEmail());
		if (existEmail != null) {
			throw new IllegalArgumentException("Email already exists");
		}
		
		String uniqueUsername = generateUniqueUsername(dto.getEmail());
		dto.setUsername(uniqueUsername);
		
		String passwordHash = encoder.encode(dto.getPassword());
		dto.setPassword(passwordHash);
		
		UserEntity userEntity = mapper.fromCreate(dto);
		
		if (dto.getRoleIds() != null && !dto.getRoleIds()
		                                    .isEmpty()) {
			List<Long> roleIds = dto.getRoleIds();
			List<RoleDto> roles = roleService.findAllById(roleIds);
			
			if (roles.isEmpty() || roles.size() != roleIds.size()) {
				throw new IllegalArgumentException("No valid roles found for the provided role IDs");
			}
			
			userEntity.addRoles(roles.stream()
			                         .map(roleMapper::toEntity)
			                         .collect(Collectors.toSet()));
		}
		
		repository.save(userEntity);
		return "Success";
	}
}
