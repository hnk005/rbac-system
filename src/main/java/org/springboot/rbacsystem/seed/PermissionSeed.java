package org.springboot.rbacsystem.seed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springboot.rbacsystem.constrant.Action;
import org.springboot.rbacsystem.constrant.ResourceOwner;
import org.springboot.rbacsystem.entity.PermissionEntity;
import org.springboot.rbacsystem.repository.PermissionRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PermissionSeed {
	
	private final PermissionRepository repository;
	
	@EventListener(ApplicationReadyEvent.class)
	@Order(1)
	@Transactional
	public void seedPermissions() {
		log.info("Bắt đầu kiểm tra và khởi tạo Permissions...");
		
		for (ResourceOwner owner : ResourceOwner.values()) {
			for (Action action : Action.values()) {
				
				String permissionName = owner.getValue() + ":" + action.getValue();
				
				if (!repository.existsByName(permissionName)) {
					
					PermissionEntity newPermission = new PermissionEntity();
					newPermission.setName(permissionName);
					newPermission.setDes("Cho phép hành động '" + action.getValue()
							+ "'"
							+ " "
							+ "trên tài nguyên '"
							+ owner.name() + "'");
					
					repository.save(newPermission);
					log.info("Đã tự động thêm quyền mới: {}", permissionName);
				}
			}
		}
		
		log.info("Hoàn tất khởi tạo Permissions!");
	}
}