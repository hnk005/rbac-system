package org.springboot.rbacsystem.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springboot.rbacsystem.security.RequirePermission;
import org.springboot.rbacsystem.security.RoleBasedAccessControlService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {
	
	private final RoleBasedAccessControlService rbaControl;
	
	@Before("@annotation(requirePermission)")
	public void checkPermission(RequirePermission requirePermission) throws AccessDeniedException {
		boolean hasAccess = rbaControl.hasAccess(requirePermission.owner(), requirePermission.action());
		
		if (!hasAccess) {
			throw new AccessDeniedException("Bạn không có quyền thực hiện hành động này!");
		}
	}
}
