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
@Table(name = "roles")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleEntity extends BaseEntity {
	
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	
	@Column(name = "description")
	private String des;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	@JoinTable(
			name = "role_permissions",
			joinColumns = @JoinColumn(name = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "permission_id")
	)
	private final Set<PermissionEntity> permissions = new HashSet<>();
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	private final Set<UserEntity> users = new HashSet<>();
	
	public void addPermission(PermissionEntity permission) {
		this.permissions.add(permission);
		permission.getRoles()
		          .add(this);
	}
	
	public void addPermissions(List<PermissionEntity> newPermissions) {
		for (PermissionEntity permission : newPermissions) {
			this.addPermission(permission);
		}
	}
	
	public void removePermission(PermissionEntity permission) {
		this.permissions.remove(permission);
		permission.getRoles()
		          .remove(this);
	}
	
	public void removePermissions(List<PermissionEntity> permissions) {
		for (PermissionEntity permission : permissions) {
			this.removePermission(permission);
		}
	}
	
	public void removePermissionAll() {
		for (PermissionEntity permission : this.permissions) {
			permission.getRoles()
			          .remove(this);
		}
		this.permissions.clear();
	}
	
	public void removeUser(UserEntity user) {
		this.users.remove(user);
		user.getRoles()
		    .remove(this);
	}
	
	public void removeUsers(List<UserEntity> users) {
		for (UserEntity user : users) {
			this.removeUser(user);
		}
	}
}
