package com.jamesngyz.qrxy.userservice.user;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import org.mapstruct.factory.Mappers;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
	
	private final UserService service;
	private final UserDtoMapper userDtoMapper = Mappers.getMapper(UserDtoMapper.class);
	
	public UserController(UserService service) {
		this.service = service;
	}
	
	@PostMapping(path = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
		
		User user = userDtoMapper.createRequestToUser(request);
		User createdUser = service.createUser(user);
		UserResponse response = userDtoMapper.userToResponse(createdUser);
		
		return ResponseEntity.created(URI.create(createdUser.getId().toString())).body(response);
	}
	
	@PutMapping(path = "/v1/users/{id}/auth_id", produces = MediaType.TEXT_PLAIN_VALUE)
	ResponseEntity<String> updateAuthId(
			@PathVariable("id") UUID userId,
			@RequestBody String authId) {
		
		String updatedAuthId = service.updateAuthId(userId, authId);
		return ResponseEntity.ok(updatedAuthId);
	}
}
