package com.jamesngyz.qrxy.userservice.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {
	
	private final TestRestTemplate restTemplate;
	private final UserRepository repository;
	
	@Autowired
	public UserControllerIT(
			TestRestTemplate restTemplate,
			UserRepository repository) {
		this.restTemplate = restTemplate;
		this.repository = repository;
	}
	
	@Test
	void createUser_AllOk_HttpStatus201AndValidBodyAndPersisted() {
		CreateUserRequest request = FakeUser.CreateRequest.build();
		ResponseEntity<UserResponse> response = restTemplate.postForEntity("/v1/users", request, UserResponse.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getHeaders().getLocation()).hasPath(response.getBody().getId().toString());
		assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
		
		assertThat(response.getBody().getAuthId()).isEqualTo(request.getAuthId());
		assertThat(response.getBody().getUsername()).isEqualTo(request.getUsername());
		assertThat(response.getBody().getEmail()).isEqualTo(request.getEmail());
		assertThat(response.getBody().getId()).isNotNull();
		assertThat(response.getBody().getCreatedAt()).isNotNull();
		assertThat(response.getBody().getCreatedBy()).isNotNull();
		assertThat(response.getBody().getUpdatedAt()).isNotNull();
		assertThat(response.getBody().getUpdatedBy()).isNotNull();
		
		assertThat(repository.findById(response.getBody().getId())).isPresent();
	}
	
	@Test
	void createUser_AuthIdNull_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withAuthIdNull();
		ResponseEntity<?> response = restTemplate.postForEntity("/v1/users", request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_UsernameNull_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameNull();
		ResponseEntity<?> response = restTemplate.postForEntity("/v1/users", request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_UsernameShorterThan3_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameShorterThan3();
		ResponseEntity<?> response = restTemplate.postForEntity("/v1/users", request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_UsernameLongerThan20_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameLongerThan20();
		ResponseEntity<?> response = restTemplate.postForEntity("/v1/users", request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_UsernameNotAlphanumeric_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameNotAlphanumeric();
		ResponseEntity<?> response = restTemplate.postForEntity("/v1/users", request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_UsernameNotLowerCase_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameNotLowerCase();
		ResponseEntity<?> response = restTemplate.postForEntity("/v1/users", request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_EmailNull_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withEmailNull();
		ResponseEntity<?> response = restTemplate.postForEntity("/v1/users", request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_EmailInvalidFormat_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withEmailInvalidFormat();
		ResponseEntity<?> response = restTemplate.postForEntity("/v1/users", request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
}
