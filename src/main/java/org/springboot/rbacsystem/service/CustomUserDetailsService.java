package org.springboot.rbacsystem.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.entity.UserEntity;
import org.springboot.rbacsystem.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Override
	@NonNull
	@Transactional
	public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
		Set<GrantedAuthority> authorities = new HashSet<>();
		
		user.getRoles()
		    .forEach(role -> {
			    role.getPermissions()
			        .forEach(permission -> {
				        authorities.add(new SimpleGrantedAuthority(permission.getName()));
			        });
		    });
		
		return new User(
				user.getUsername(),
				user.getPassword(),
				authorities
		);
	}
}