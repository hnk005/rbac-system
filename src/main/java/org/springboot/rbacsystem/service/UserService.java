package org.springboot.rbacsystem.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.SQLGrammarException;
import org.springboot.rbacsystem.dto.CreateUserDto;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.dto.UpdateUserDto;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.entity.UserEntity;
import org.springboot.rbacsystem.mapper.role.RoleMapper;
import org.springboot.rbacsystem.mapper.user.UserMapper;
import org.springboot.rbacsystem.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
			
			if (!repository.existsByUsername(generatedUsername)) {
				isUnique = true;
			}
			
			// Add a safety break condition for infinite loops
			if (attempts++ > 10) throw new RuntimeException("Could not generate unique username");
		} while (!isUnique);
		
		return generatedUsername;
	}
	
	public List<UserDto> findAll() {
		List<UserEntity> userEntities = repository.findAll();
		
		
		return userEntities.stream()
		                   .map(userEntity -> {
			                   List<RoleDto> roles = userEntity.getRoles()
			                                                   .stream()
			                                                   .map(roleMapper::toDto)
			                                                   .toList();
			                   UserDto userDto = mapper.toDto(userEntity);
			                   userDto.setRoles(roles);
			                   return userDto;
		                   })
		                   .toList();
	}
	
	public UserDto findById(Long id) throws IllegalArgumentException {
		UserEntity userEntity = repository.findById(id)
		                                  .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
		List<RoleDto> roles = userEntity.getRoles()
		                                .stream()
		                                .map(roleMapper::toDto)
		                                .toList();
		UserDto userDto = mapper.toDto(userEntity);
		userDto.setRoles(roles);
		return userDto;
	}
	
	public String create(CreateUserDto dto) throws IllegalArgumentException, SQLGrammarException {
		
		boolean existUserEntity = repository.existsByEmail(dto.getEmail());
		if (existUserEntity) {
			throw new IllegalArgumentException("Email already exists");
		}
		
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(dto.getEmail());
		userEntity.setFullName(dto.getFullName());
		
		String uniqueUsername = generateUniqueUsername(userEntity.getEmail());
		userEntity.setUsername(uniqueUsername);
		
		String passwordHash = encoder.encode(dto.getPassword());
		userEntity.setPassword(passwordHash);
		
		
		if (dto.getRoleIds() != null && !dto.getRoleIds()
		                                    .isEmpty()) {
			List<Long> roleIds = dto.getRoleIds();
			List<RoleDto> roles = roleService.findAllById(roleIds);
			
			if (roles.isEmpty() || roles.size() != roleIds.size()) {
				throw new IllegalArgumentException("No valid roles found for the provided role IDs");
			}
			
			userEntity.addRoles(roles.stream()
			                         .map(roleMapper::toEntity)
			                         .toList());
		}
		
		repository.save(userEntity);
		return "Success";
	}
	
	public String update(Long id, UpdateUserDto dto) throws IllegalArgumentException {
		UserEntity userEntity = repository.findById(id)
		                                  .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
		
		if (dto.getEmail() != null && !dto.getEmail()
		                                  .equals(userEntity.getEmail())) {
			boolean existEmail = repository.existsByEmail(dto.getEmail());
			if (existEmail) {
				throw new IllegalArgumentException("Email already exists");
			}
			userEntity.setEmail(dto.getEmail());
		}
		
		if (dto.getPassword() != null) {
			String passwordHash = encoder.encode(dto.getPassword());
			userEntity.setPassword(passwordHash);
		}
		
		if (dto.getFullName() != null) {
			userEntity.setFullName(dto.getFullName());
		}
		
		if (dto.getUsername() != null) {
			userEntity.setUsername(dto.getUsername());
		}
		
		if (dto.getRoleIds() != null && !dto.getRoleIds()
		                                    .isEmpty()) {
			List<Long> roleIds = dto.getRoleIds();
			
			List<RoleDto> roles = roleService.findAllById(roleIds);
			
			if (roles.size() < roleIds.size()) {
				throw new IllegalArgumentException("No valid roles found for the provided role IDs");
			}
			
			userEntity.removeRoles(userEntity.getRoles()
			                                 .stream()
			                                 .toList());
			
			userEntity.addRoles(roles.stream()
			                         .map(roleMapper::toEntity)
			                         .toList());
		}
		
		repository.save(userEntity);
		return "Success";
	}
	
	public String delete(List<Long> ids) throws IllegalArgumentException {
		List<UserEntity> userEntities = repository.findAllById(ids);
		
		if (userEntities.isEmpty()) {
			throw new IllegalArgumentException("No users found for the provided IDs");
		}
		
		userEntities.forEach(userEntity ->
				userEntity.removeRoles(
						userEntity.getRoles()
						          .stream()
						          .toList()));
		
		repository.deleteAll(userEntities);
		return "Success";
	}
}
