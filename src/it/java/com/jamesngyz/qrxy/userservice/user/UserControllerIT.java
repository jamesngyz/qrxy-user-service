package com.jamesngyz.qrxy.userservice.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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
	
}
