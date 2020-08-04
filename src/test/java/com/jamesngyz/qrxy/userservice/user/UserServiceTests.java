package com.jamesngyz.qrxy.userservice.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {
	
	@InjectMocks
	private UserService subject;
	
	@Mock
	private UserRepository repository;
	
	@Test
	void createUser_AllOk_PersistAndReturn() {
		CreateUserRequest request = FakeUser.CreateRequest.build();
		assertPersistAndReturn(request);
	}
	
	@Test
	void createUser_EssentialFieldsOnly_PersistAndReturn() {
		CreateUserRequest request = FakeUser.CreateRequest.withAuthIdNull();
		assertPersistAndReturn(request);
	}
	
	private void assertPersistAndReturn(CreateUserRequest request) {
		User input = FakeUser.fromRequest(request);
		
		User expected = FakeUser.build();
		expected.setUsername(input.getUsername());
		expected.setEmail(input.getEmail());
		expected.setAuthId(input.getAuthId());
		
		when(repository.save(notNull())).thenReturn(expected);
		
		User result = subject.createUser(input);
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void updateUserAuthId_AllOk_PersistAndReturn() {
		User initialUser = FakeUser.build();
		
		UUID userId = initialUser.getId();
		String authId = UUID.randomUUID().toString();
		
		User updatedUser = FakeUser.from(initialUser);
		updatedUser.setAuthId(authId);
		
		when(repository.getOne(userId)).thenReturn(initialUser);
		when(repository.save(updatedUser)).thenReturn(updatedUser);
		
		String result = subject.updateAuthId(userId, authId);
		assertThat(result).isEqualTo(authId);
	}
	
}
