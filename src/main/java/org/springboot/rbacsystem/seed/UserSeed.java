package org.springboot.rbacsystem.seed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springboot.rbacsystem.constrant.RoleEnum;
import org.springboot.rbacsystem.entity.RoleEntity;
import org.springboot.rbacsystem.entity.UserEntity;
import org.springboot.rbacsystem.repository.RoleRepository;
import org.springboot.rbacsystem.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSeed {
	
	private final PasswordEncoder encoder;
	private final UserRepository repository;
	private final RoleRepository roleRepository;
	
	@EventListener(ApplicationReadyEvent.class)
	@Order(3)
	@Transactional
	void seedUsers() {
		log.info("Starting to initialize Users...");
		
		if (repository.existsByUsername("admin") || repository.existsByUsername("test")) {
			log.info("Admin or Test user already exists. Skipping initialization.");
			return;
		}
		
		RoleEntity adminRole = roleRepository.findByName(RoleEnum.ADMIN.getValue());
		RoleEntity testRole = roleRepository.findByName(RoleEnum.USER.getValue());
		
		if (adminRole == null) {
			log.error("Admin role not found. Please ensure that roles are seeded before seeding users.");
			return;
		}
		
		UserEntity adminUser = new UserEntity();
		adminUser.setEmail("admin@example.com");
		adminUser.setUsername("admin");
		adminUser.setPassword(encoder.encode("admin123"));
		adminUser.setFullName("Admin");
		adminUser.addRole(adminRole);
		
		UserEntity testUser = new UserEntity();
		testUser.setEmail("test@example.com");
		testUser.setUsername("test");
		testUser.setPassword(encoder.encode("test123"));
		testUser.setFullName("Test");
		testUser.addRole(testRole);
		
		repository.save(adminUser);
		repository.save(testUser);
		
		log.info("Users initialized successfully: {}, {}", adminUser.getUsername(), testUser.getUsername());
		
	}
}
