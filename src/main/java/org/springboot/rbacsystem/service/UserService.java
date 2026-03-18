package org.springboot.rbacsystem.service;

import lombok.AllArgsConstructor;
import org.hibernate.exception.SQLGrammarException;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.entity.UserEntity;
import org.springboot.rbacsystem.mapper.user.UserMapper;
import org.springboot.rbacsystem.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
	
	private final UserRepository repository;
	private final BCryptPasswordEncoder encoder;
	private final UserMapper mapper;
	
	public String generateUniqueUsername(String email) {
		String baseUsername = email.split("@")[0];
		String generatedUsername;
		boolean isUnique = false;
		int attempts = 0;
		
		do {
			// Append a random number (e.g., 5 digits)
			generatedUsername = baseUsername + (int) (Math.random() * 90000) + 10000;
			// Check if it exists in the database
			if (repository.findByUsername(generatedUsername) == null) {
				isUnique = true;
			}
			// Add a safety break condition for infinite loops
			if (attempts++ > 10) throw new RuntimeException("Could not generate unique username");
		} while (!isUnique);
		
		return generatedUsername;
	}
	
	public void create(UserDto dto) throws IllegalArgumentException, SQLGrammarException {
		
		//For register, we only need email and password, so we can skip validation for other fields
		
		if (dto == null) {
			throw new IllegalArgumentException("UserDto cannot be null");
		}
		
		UserEntity existEmail = repository.findByEmail(dto.getEmail());
		if (existEmail != null) {
			throw new IllegalArgumentException("Email already exists");
		}
		
		String uniqueUsername = generateUniqueUsername(dto.getEmail());
		dto.setUsername(uniqueUsername);
		
		String passwordHash = encoder.encode(dto.getPassword());
		dto.setPassword(passwordHash);
		
		repository.save(mapper.toEntity(dto));
	}
}
