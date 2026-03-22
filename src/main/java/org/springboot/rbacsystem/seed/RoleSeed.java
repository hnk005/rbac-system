package org.springboot.rbacsystem.seed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springboot.rbacsystem.constrant.ResourceOwner;
import org.springboot.rbacsystem.constrant.RoleEnum;
import org.springboot.rbacsystem.entity.PermissionEntity;
import org.springboot.rbacsystem.entity.RoleEntity;
import org.springboot.rbacsystem.repository.PermissionRepository;
import org.springboot.rbacsystem.repository.RoleRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleSeed {
	
	private final RoleRepository repository;
	private final PermissionRepository permissionRepository;
	
	@EventListener(ApplicationReadyEvent.class)
	@Order(2)
	@Transactional
	void seedRoles() {
		log.info("Starting to initialize Roles...");
		
		if (repository.existsByName(RoleEnum.ADMIN.getValue()) || repository.existsByName(RoleEnum.USER.getValue())) {
			log.info("Admin or User role already exists. Skipping initialization.");
			return;
		}
		
		List<PermissionEntity> aminPermissions = permissionRepository.findByNameEndingWith("*");
		List<PermissionEntity> userPermissions = List.of(
				permissionRepository.findByName(ResourceOwner.USER.getValue() + ":*"),
				permissionRepository.findByName(ResourceOwner.ROLE.getValue() + ":read")
		);
		
		
		RoleEntity adminRole = new RoleEntity();
		adminRole.setName(RoleEnum.ADMIN.getValue());
		adminRole.setDes("Permission for admin with full access to all resources");
		adminRole.addPermissions(aminPermissions);
		
		RoleEntity userRole = new RoleEntity();
		userRole.setName(RoleEnum.USER.getValue());
		userRole.setDes("Permission for user with limited access to resources");
		userRole.addPermissions(userPermissions);
		
		repository.save(adminRole);
		repository.save(userRole);
		
		log.info("Roles initialized successfully: {}, {}", adminRole.getName(), userRole.getName());
		log.info("Finished initializing Roles.");
	}
}
