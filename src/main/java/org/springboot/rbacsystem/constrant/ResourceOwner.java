package org.springboot.rbacsystem.constrant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResourceOwner {
	SYS("system"),
	USER("user"),
	ROLE("role");
	private final String value;
}
