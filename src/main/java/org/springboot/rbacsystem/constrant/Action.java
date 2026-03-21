package org.springboot.rbacsystem.constrant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Action {
	READ("read"),
	CREATE("create"),
	UPDATE("update"),
	DELETE("delete"),
	ALL("*");
	
	private final String value;
}
