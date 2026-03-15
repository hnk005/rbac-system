package org.springboot.rbacsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
public class Permission extends EntityIdAutoIncrement {
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	
	@Column(name = "description")
	private String des;
	
	@ManyToMany(mappedBy = "permissions")
	private Set<Role> roles = new HashSet<>();
}
