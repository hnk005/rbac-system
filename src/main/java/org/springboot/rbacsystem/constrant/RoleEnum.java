package org.springboot.rbacsystem.constrant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
	ADMIN("admin"),
	USER("user");
	
	private final String value;
}
