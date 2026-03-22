package org.springboot.rbacsystem.exception;

import io.jsonwebtoken.JwtException;
import org.hibernate.exception.SQLGrammarException;
import org.springboot.rbacsystem.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception ex) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage(), null);
	}
	
	@ExceptionHandler(SQLGrammarException.class)
	public ResponseEntity<ErrorResponseDto> handleSQLGrammarException(SQLGrammarException ex) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR, "Database error: " + ex.getMessage(), null);
	}
	
	@ExceptionHandler(InsufficientAuthenticationException.class)
	public ResponseEntity<ErrorResponseDto> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
		return error(HttpStatus.UNAUTHORIZED, "Authentication required: " + ex.getMessage(), null);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult()
		  .getFieldErrors()
		  .forEach(error ->
				  errors.put(error.getField(), error.getDefaultMessage()));
		
		return error(HttpStatus.BAD_REQUEST, "Validation failed", errors);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
		return error(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
	}
	
	@ExceptionHandler(ServiceArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleServiceArgumentNotValidException(ServiceArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put(ex.getField(), ex.getMessage());
		
		return error(HttpStatus.BAD_REQUEST, "Service argument validation failed", errors);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		return error(HttpStatus.NOT_FOUND, ex.getMessage(), null);
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ErrorResponseDto> handleJwtException(JwtException ex) {
		return error(HttpStatus.UNAUTHORIZED, "Invalid or expired JWT token", null);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex) {
		return error(HttpStatus.FORBIDDEN, "Access denied: " + ex.getMessage(), null);
	}
	
	private ResponseEntity<ErrorResponseDto> error(HttpStatus status, String message,
	                                               Map<String, String> errors) {
		ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
		                                                    .message(message)
		                                                    .errors(errors)
		                                                    .status(status.value())
		                                                    .build();
		return new ResponseEntity<>(errorResponseDto, status);
	}
}
