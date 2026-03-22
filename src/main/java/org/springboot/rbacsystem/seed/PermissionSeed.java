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
		log.info("Starting to initialize Permissions...");
		
		for (ResourceOwner owner : ResourceOwner.values()) {
			for (Action action : Action.values()) {
				
				String permissionName = owner.getValue() + ":" + action.getValue();
				
				if (!repository.existsByName(permissionName)) {
					
					PermissionEntity newPermission = new PermissionEntity();
					newPermission.setName(permissionName);
					newPermission.setDes("Permission for " + owner.getValue() + " - " + action.getValue());
					
					repository.save(newPermission);
					log.info("Created permission: {}", permissionName);
				}
			}
		}
		
		log.info("Finished initializing Permissions.");
	}
}