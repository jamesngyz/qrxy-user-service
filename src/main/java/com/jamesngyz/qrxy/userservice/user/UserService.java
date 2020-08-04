package com.jamesngyz.qrxy.userservice.user;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
class UserService {
	
	private final UserRepository repository;
	
	UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	User createUser(User user) {
		user.setId(UUID.randomUUID());
		return repository.save(user);
	}
	
	String updateAuthId(UUID userId, String authId) {
		User user = repository.getOne(userId);
		user.setAuthId(authId);
		User savedUser = repository.save(user);
		
		return savedUser.getAuthId();
	}
}
