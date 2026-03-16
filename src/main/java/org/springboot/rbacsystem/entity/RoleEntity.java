package org.springboot.rbacsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleEntity extends BaseEntity {
	
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	
	@Column(name = "description")
	private String des;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "role_permission",
			joinColumns = @JoinColumn(name = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "permission_id")
	)
	private Set<PermissionEntity> permissions = new HashSet<>();
	
	@ManyToMany(mappedBy = "roles")
	private Set<UserEntity> users = new HashSet<>();
}
