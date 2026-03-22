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
		log.info("Bắt đầu kiểm tra và khởi tạo Roles...");
		
		if (repository.count() == 0) {
			List<PermissionEntity> aminPermission = permissionRepository.findByNameEndingWith("*");
			PermissionEntity userPermission = permissionRepository.findByName(ResourceOwner.USER.getValue() + ":*");
			
			RoleEntity adminRole = new RoleEntity();
			adminRole.setName(RoleEnum.ADMIN.getValue());
			adminRole.setDes("Quyền quản trị viên với tất cả quyền hạn");
			adminRole.addPermissions(aminPermission);
			
			RoleEntity userRole = new RoleEntity();
			userRole.setName(RoleEnum.USER.getValue());
			userRole.setDes("Quyền người dùng với quyền hạn cơ bản");
			userRole.addPermission(userPermission);
			
			repository.save(adminRole);
			repository.save(userRole);
			
			log.info("Đã tự động thêm các role mặc định: Admin, User");
		} else {
			log.info("Roles đã tồn tại, không cần khởi tạo.");
		}
		
		log.info("Hoàn tất khởi tạo Roles!");
	}
}
