package org.springboot.rbacsystem.security;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.constrant.Action;
import org.springboot.rbacsystem.constrant.ResourceOwner;
import org.springboot.rbacsystem.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("rbaControl")
@RequiredArgsConstructor
public class RoleBasedAccessControlService {
	
	private final UserRepository userRepository;
	
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

//	public boolean isOwner(String resourceType, Long resourceId) {
//		Authentication authentication = SecurityContextHolder.getContext()
//		                                                     .getAuthentication();
//		if (authentication == null || !authentication.isAuthenticated()) return false;
//
//		String currentUsername = authentication.getName();
//
//		switch (resourceType.toUpperCase()) {
//			case "USER":
//				return userRepository.existsByIdAndUsername(resourceId, currentUsername);
//			// Thêm các case khác khi dự án mở rộng
//			default:
//				return false;
//		}
//	}
}
