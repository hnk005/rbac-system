package org.springboot.rbacsystem.repository;

import org.springboot.rbacsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
}