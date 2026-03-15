package org.springboot.rbacsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springboot.rbacsystem.helper.EntityIdAutoIncrement;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Role extends EntityIdAutoIncrement {
	
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
	private Set<Permission> permissions = new HashSet<>();
	
	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<>();
}
