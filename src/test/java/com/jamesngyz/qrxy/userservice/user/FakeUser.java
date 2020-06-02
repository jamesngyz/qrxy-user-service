package com.jamesngyz.qrxy.userservice.user;

import java.util.UUID;

import com.github.javafaker.Faker;

class FakeUser {
	
	private static Faker faker = new Faker();
	
	static User build() {
		String authId = UUID.randomUUID().toString();
		String username = faker.name().username();
		String email = faker.internet().emailAddress();
		
		User user = new User();
		user.setAuthId(authId);
		user.setUsername(username);
		user.setEmail(email);
		setSystemGeneratedFields(user);
		return user;
	}
	
	static User fromRequest(CreateUserRequest request) {
		User user = new User();
		user.setAuthId(request.getAuthId());
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		return user;
	}
	
	static User fromRequestAndSetSystemGeneratedFields(CreateUserRequest request) {
		User user = fromRequest(request);
		setSystemGeneratedFields(user);
		return user;
	}
	
	private static void setSystemGeneratedFields(User user) {
		user.setId(UUID.randomUUID());
		user.setCreatedAt(faker.date().birthday());
		user.setCreatedBy(faker.name().firstName());
		user.setUpdatedAt(user.getCreatedAt());
		user.setUpdatedBy(user.getCreatedBy());
	}
	
	static class CreateRequest {
		static CreateUserRequest build() {
			String authId = UUID.randomUUID().toString();
			String username = faker.name().username();
			String email = faker.internet().emailAddress();
			
			CreateUserRequest request = new CreateUserRequest();
			request.setAuthId(authId);
			request.setUsername(username);
			request.setEmail(email);
			
			return request;
		}
	}
	
	static class Response {
		static UserResponse fromUser(User user) {
			UserResponse response = new UserResponse();
			response.setAuthId(user.getAuthId());
			response.setUsername(user.getUsername());
			response.setEmail(user.getEmail());
			response.setBio(user.getBio());
			response.setId(user.getId());
			response.setCreatedAt(user.getCreatedAt());
			response.setCreatedBy(user.getCreatedBy());
			response.setUpdatedAt(user.getUpdatedAt());
			response.setUpdatedBy(user.getUpdatedBy());
			
			return response;
		}
	}
	
}
