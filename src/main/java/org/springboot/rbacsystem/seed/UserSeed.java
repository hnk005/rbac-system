package org.springboot.rbacsystem.seed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springboot.rbacsystem.constrant.RoleEnum;
import org.springboot.rbacsystem.entity.RoleEntity;
import org.springboot.rbacsystem.entity.UserEntity;
import org.springboot.rbacsystem.repository.RoleRepository;
import org.springboot.rbacsystem.repository.UserRepository;
import org.springboot.rbacsystem.service.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSeed {
	
	private final UserService service;
	private final UserRepository repository;
	private final RoleRepository roleRepository;
	
	@EventListener(ApplicationReadyEvent.class)
	@Order(3)
	@Transactional
	void seedUsers() {
		log.info("Bắt đầu kiểm tra và khởi tạo Users...");
		
		if (repository.count() == 0) {
			RoleEntity adminRole = roleRepository.findByName(RoleEnum.ADMIN.getValue());
			RoleEntity testRole = roleRepository.findByName(RoleEnum.USER.getValue());
			
			if (adminRole == null) {
				log.error("Không tìm thấy role Admin không thể khởi tạo");
				return;
			}
			
			UserEntity adminUser = new UserEntity();
			adminUser.setEmail("admin@example.com");
			adminUser.setPassword(service.generatePassword("admin123"));
			adminUser.setFullName("Admin");
			adminUser.setUsername(service.generateUniqueUsername(adminUser.getEmail()));
			adminUser.addRole(adminRole);
			
			UserEntity testUser = new UserEntity();
			testUser.setEmail("test@example.com");
			testUser.setPassword(service.generatePassword("test123"));
			testUser.setFullName("Test");
			testUser.setUsername(service.generateUniqueUsername(testUser.getEmail()));
			testUser.addRole(testRole);
			
			repository.save(adminUser);
			repository.save(testUser);
		}
	}
}
