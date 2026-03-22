package org.springboot.rbacsystem.security;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.constrant.Action;
import org.springboot.rbacsystem.constrant.ResourceOwner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("rbaControl")
@RequiredArgsConstructor
public class RoleBasedAccessControlService {
	
	public boolean hasAccess(ResourceOwner owner, Action action) {
		Authentication authentication = SecurityContextHolder.getContext()
		                                                     .getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) return false;
		
		String requiredPermission = owner.getValue() + ":" + action.getValue();
		String wildcardPermission = owner.getValue() + ":*";
		
		return authentication.getAuthorities()
		                     .stream()
		                     .anyMatch(grantedAuthority ->
				                     Objects.equals(grantedAuthority.getAuthority(), requiredPermission) ||
						                     Objects.equals(grantedAuthority.getAuthority(), wildcardPermission)
		                     );
	}
}
