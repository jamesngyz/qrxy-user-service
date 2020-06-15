package com.jamesngyz.qrxy.userservice.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

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
		User input = FakeUser.fromRequest(request);
		
		User expected = FakeUser.build();
		expected.setUsername(input.getUsername());
		expected.setEmail(input.getEmail());
		expected.setAuthId(input.getAuthId());
		
		when(repository.save(notNull())).thenReturn(expected);
		
		User result = subject.createUser(input);
		assertThat(result).isEqualTo(expected);
	}
	
}
