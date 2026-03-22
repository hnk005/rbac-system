package org.springboot.rbacsystem.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.SQLGrammarException;
import org.springboot.rbacsystem.dto.*;
import org.springboot.rbacsystem.entity.RoleEntity;
import org.springboot.rbacsystem.entity.UserEntity;
import org.springboot.rbacsystem.exception.ServiceArgumentNotValidException;
import org.springboot.rbacsystem.mapper.role.RoleMapper;
import org.springboot.rbacsystem.mapper.user.UserMapper;
import org.springboot.rbacsystem.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	
	private final RoleService roleService;
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	private final UserMapper mapper;
	private final RoleMapper roleMapper;
	
	public List<UserDto> findAll() {
		List<UserEntity> userEntities = repository.findAll();
		
		
		return userEntities.stream()
		                   .map(userEntity -> {
			                   List<RoleDto> roles = userEntity.getRoles()
			                                                   .stream()
			                                                   .map(roleMapper::toDto)
			                                                   .toList();
			                   UserDto userDto = mapper.toDto(userEntity);
			                   List<UserRolesDto> userRolesDtos = roles.stream()
			                                                           .map(role -> {
				                                                           UserRolesDto userRolesDto =
						                                                           new UserRolesDto();
				                                                           userRolesDto.setId(role.getId());
				                                                           userRolesDto.setName(role.getName());
				                                                           return userRolesDto;
			                                                           })
			                                                           .toList();
			                   
			                   userDto.setRoles(userRolesDtos);
			                   return userDto;
		                   })
		                   .toList();
	}
	
	public UserDto findById(Long id) throws IllegalArgumentException {
		UserEntity userEntity = repository.findById(id)
		                                  .orElseThrow(() -> new ServiceArgumentNotValidException("user_id", "User not" +
				                                  " " +
				                                  "found with id: " + id));
		List<RoleDto> roles = userEntity.getRoles()
		                                .stream()
		                                .map(roleMapper::toDto)
		                                .toList();
		UserDto userDto = mapper.toDto(userEntity);
		
		List<UserRolesDto> userRolesDtos = roles.stream()
		                                        .map(role -> {
			                                        UserRolesDto userRolesDto = new UserRolesDto();
			                                        userRolesDto.setId(role.getId());
			                                        userRolesDto.setName(role.getName());
			                                        return userRolesDto;
		                                        })
		                                        .toList();
		
		userDto.setRoles(userRolesDtos);
		return userDto;
	}
	
	public UserDto findByCurrentUser() {
		String username = Objects.requireNonNull(SecurityContextHolder.getContext()
		                                                              .getAuthentication())
		                         .getName();
		
		UserEntity userEntity = repository.findByUsername(username);
		
		if (userEntity == null) {
			throw new ServiceArgumentNotValidException("current_user", "Current user not found");
		}
		
		List<RoleDto> roles = userEntity.getRoles()
		                                .stream()
		                                .map(roleMapper::toDto)
		                                .toList();
		
		UserDto userDto = mapper.toDto(userEntity);
		
		List<UserRolesDto> userRolesDtos = roles.stream()
		                                        .map(role -> {
			                                        UserRolesDto userRolesDto = new UserRolesDto();
			                                        userRolesDto.setId(role.getId());
			                                        userRolesDto.setName(role.getName());
			                                        return userRolesDto;
		                                        })
		                                        .toList();
		
		userDto.setRoles(userRolesDtos);
		return userDto;
	}
	
	public String create(CreateUserDto dto) throws ServiceArgumentNotValidException, SQLGrammarException {
		
		boolean existUserEntity = repository.existsByEmail(dto.getEmail());
		if (existUserEntity) {
			throw new ServiceArgumentNotValidException(CreateUserDto.Fields.email, "Email already exists");
		}
		
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(dto.getEmail());
		userEntity.setFullName(dto.getFullName());
		
		boolean existUsername = repository.existsByUsername(dto.getUsername());
		if (existUsername) {
			throw new ServiceArgumentNotValidException(CreateUserDto.Fields.username, "Username already exists");
		}
		
		userEntity.setUsername(dto.getUsername());
		
		String passwordHash = encoder.encode(dto.getPassword());
		userEntity.setPassword(passwordHash);
		
		if (dto.getRoleIds() != null && !dto.getRoleIds()
		                                    .isEmpty()) {
			List<Long> roleIds = dto.getRoleIds();
			List<RoleEntity> roles = roleService.findAllById(roleIds);
			
			if (roles.isEmpty() || roles.size() != roleIds.size()) {
				throw new ServiceArgumentNotValidException(CreateUserDto.Fields.roleIds, "No valid roles found for " +
						"the" +
						" provided user");
			}
			
			userEntity.addRoles(roles);
		}
		
		repository.save(userEntity);
		return "Success";
	}
	
	public String update(Long id, UpdateUserDto dto) throws ServiceArgumentNotValidException, SQLGrammarException {
		UserEntity userEntity = repository.findById(id)
		                                  .orElseThrow(() -> new ServiceArgumentNotValidException("id", "User not " +
				                                  "found with id: " + id));
		
		if (dto.getEmail() != null && !dto.getEmail()
		                                  .equals(userEntity.getEmail())) {
			boolean existEmail = repository.existsByEmail(dto.getEmail());
			if (existEmail) {
				throw new ServiceArgumentNotValidException(UpdateUserDto.Fields.email, "Email already exists");
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
		
		if (dto.getRoleIds() != null) {
			
			userEntity.clearRoles();
			
			if (!dto.getRoleIds()
			        .isEmpty()) {
				
				List<Long> roleIds = dto.getRoleIds();
				
				List<RoleEntity> roles = roleService.findAllById(roleIds);
				
				if (roles.size() != roleIds.size()) {
					throw new ServiceArgumentNotValidException(UpdateUserDto.Fields.roleIds, "No valid roles found " +
							"for" +
							" the" +
							" provided user");
				}
				
				userEntity.addRoles(roles);
			}
		}
		
		repository.save(userEntity);
		return "Success";
	}
	
	public String delete(List<Long> ids) throws ServiceArgumentNotValidException, SQLGrammarException {
		List<UserEntity> userEntities = repository.findAllById(ids);
		
		if (userEntities.isEmpty()) {
			throw new ServiceArgumentNotValidException("user_ids", "No users found for the provided ids");
		}
		
		userEntities.forEach(UserEntity::clearRoles);
		
		repository.deleteAll(userEntities);
		return "Success";
	}
}
