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
public class UserEntity extends BaseEntity {
	
	@Column(length = 50, nullable = false, unique = true)
	private String username;
	
	@Column(length = 64, nullable = false)
	private String password;
	
	@Column(name = "full_name", length = 50, nullable = false)
	private String fullName;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<RoleEntity> roles = new HashSet<>();
}
