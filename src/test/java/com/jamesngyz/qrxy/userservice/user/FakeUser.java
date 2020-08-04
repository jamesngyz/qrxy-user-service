package com.jamesngyz.qrxy.userservice.user;

import java.util.Random;
import java.util.UUID;

import com.github.javafaker.Faker;

class FakeUser {
	
	private static Faker faker = new Faker();
	private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]*$";
	
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
	
	static User from(User source) {
		User clone = new User();
		
		clone.setId(source.getId());
		clone.setAuthId(source.getAuthId());
		clone.setUsername(source.getUsername());
		clone.setEmail(source.getEmail());
		clone.setBio(source.getBio());
		
		clone.setCreatedAt(source.getCreatedAt());
		clone.setCreatedBy(source.getCreatedBy());
		clone.setUpdatedAt(source.getCreatedAt());
		clone.setUpdatedBy(source.getCreatedBy());
		
		return clone;
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
	
	private static String username() {
		int length = faker.number().numberBetween(3, 20);
		return faker.lorem().characters(length).toLowerCase();
	}
	
	private static String usernameShorterThan3() {
		int length = faker.number().numberBetween(0, 3);
		return username().substring(0, length);
	}
	
	private static String usernameLongerThan20() {
		int length = faker.number().numberBetween(21, 101);
		return faker.lorem().characters(length);
	}
	
	private static String usernameNotAlphanumeric() {
		while (true) {
			int codePoint = new Random().nextInt(Character.MAX_CODE_POINT);
			
			switch (Character.getType(codePoint)) {
				case Character.UNASSIGNED:
				case Character.PRIVATE_USE:
				case Character.SURROGATE:
					continue;
			}
			
			String character = String.valueOf(Character.toChars(codePoint));
			if (!character.matches(ALPHANUMERIC_REGEX)) {
				return username() + character;
			}
		}
	}
	
	private static String usernameNotLowerCase() {
		String username = username();
		int index = faker.number().numberBetween(0, username.length());
		char upperCaseLetter = faker.lorem().word().toUpperCase().charAt(0);
		
		return username.substring(0, index) + upperCaseLetter + username.substring(index);
	}
	
	static class CreateRequest {
		static CreateUserRequest build() {
			String authId = UUID.randomUUID().toString();
			String username = username();
			String email = faker.internet().emailAddress();
			
			CreateUserRequest request = new CreateUserRequest();
			request.setAuthId(authId);
			request.setUsername(username);
			request.setEmail(email);
			return request;
		}
		
		static CreateUserRequest withAuthIdNull() {
			CreateUserRequest request = build();
			request.setAuthId(null);
			return request;
		}
		
		static CreateUserRequest withUsernameNull() {
			CreateUserRequest request = build();
			request.setUsername(null);
			return request;
		}
		
		static CreateUserRequest withUsernameShorterThan3() {
			CreateUserRequest request = build();
			request.setUsername(usernameShorterThan3());
			return request;
		}
		
		static CreateUserRequest withUsernameLongerThan20() {
			CreateUserRequest request = build();
			request.setUsername(usernameLongerThan20());
			return request;
		}
		
		static CreateUserRequest withUsernameNotAlphanumeric() {
			CreateUserRequest request = build();
			request.setUsername(usernameNotAlphanumeric());
			return request;
		}
		
		static CreateUserRequest withUsernameNotLowerCase() {
			CreateUserRequest request = build();
			request.setUsername(usernameNotLowerCase());
			return request;
		}
		
		static CreateUserRequest withEmailNull() {
			CreateUserRequest request = build();
			request.setEmail(null);
			return request;
		}
		
		static CreateUserRequest withEmailInvalidFormat() {
			CreateUserRequest request = build();
			request.setEmail(faker.lorem().word());
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
