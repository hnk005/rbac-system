package org.springboot.rbacsystem.repository;

import org.springboot.rbacsystem.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
	boolean existsByName(String name);
}


