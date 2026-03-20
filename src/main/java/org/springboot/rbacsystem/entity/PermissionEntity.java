package org.springboot.rbacsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
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
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	private final Set<RoleEntity> roles = new HashSet<>();
	
	public void removeRole(RoleEntity role) {
		role.getPermissions()
		    .remove(this);
		this.roles.remove(role);
	}
	
	public void removeRoles(List<RoleEntity> roles) {
		for (RoleEntity role : roles) {
			this.removeRole(role);
		}
	}
}
