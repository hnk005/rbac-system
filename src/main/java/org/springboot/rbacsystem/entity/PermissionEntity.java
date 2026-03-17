package org.springboot.rbacsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "permissions")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PermissionEntity extends BaseEntity {
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	
	@Column(name = "description")
	private String des;
	
	@ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
	Set<RoleEntity> roles = new HashSet<>();
}
