package org.springboot.rbacsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends BaseEntity {
	
	@Column(length = 50, nullable = false, unique = true)
	private String username;
	
	@Column(length = 60, nullable = false)
	private String password;
	
	@Column(name = "full_name", length = 100, nullable = false)
	private String fullName;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@EqualsAndHashCode.Exclude
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	@JoinTable(
			name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private final Set<RoleEntity> roles = new HashSet<>();
	
	public void addRole(RoleEntity role) {
		this.roles.add(role);
		role.getUsers()
		    .add(this);
	}
	
	public void addRoles(Set<RoleEntity> newRoles) {
		for (RoleEntity role : newRoles) {
			this.addRole(role);
		}
	}
}
