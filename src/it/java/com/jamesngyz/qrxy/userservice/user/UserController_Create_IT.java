package com.jamesngyz.qrxy.userservice.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import com.jamesngyz.qrxy.userservice.IntegrationTestConfig;

@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserController_Create_IT {
	
	private final IntegrationTestConfig config;
	private final TestRestTemplate restTemplate;
	private final UserRepository repository;
	
	@Autowired
	public UserController_Create_IT(
			IntegrationTestConfig config,
			TestRestTemplate restTemplate,
			UserRepository repository) {
		this.config = config;
		this.restTemplate = restTemplate;
		this.repository = repository;
	}
	
	@Test
	void createUser_NoJWT_HttpStatus401() {
		CreateUserRequest request = FakeUser.CreateRequest.build();
		ResponseEntity<UserResponse> response = restTemplate.postForEntity("/v1/users", request, UserResponse.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	void createUser_AllOk_HttpStatus201AndValidBodyAndPersisted() {
		CreateUserRequest request = FakeUser.CreateRequest.build();
		ResponseEntity<UserResponse> response = postForEntity(request, UserResponse.class);
		
		assertHttpStatus201AdnValidBodyAndPersisted(request, response);
	}
	
	@Test
	void createUser_AuthIdNull_HttpStatus201AndValidBodyAndPersisted() {
		CreateUserRequest request = FakeUser.CreateRequest.withAuthIdNull();
		ResponseEntity<UserResponse> response = postForEntity(request, UserResponse.class);
		
		assertHttpStatus201AdnValidBodyAndPersisted(request, response);
	}
	
	private void assertHttpStatus201AdnValidBodyAndPersisted(CreateUserRequest request,
			ResponseEntity<UserResponse> response) {
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
	void createUser_UsernameNull_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameNull();
		ResponseEntity<?> response = postForEntity(request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_UsernameShorterThan3_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameShorterThan3();
		ResponseEntity<?> response = postForEntity(request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_UsernameLongerThan20_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameLongerThan20();
		ResponseEntity<?> response = postForEntity(request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_UsernameNotAlphanumeric_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameNotAlphanumeric();
		ResponseEntity<?> response = postForEntity(request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_UsernameNotLowerCase_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameNotLowerCase();
		ResponseEntity<?> response = postForEntity(request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_EmailNull_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withEmailNull();
		ResponseEntity<?> response = postForEntity(request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createUser_EmailInvalidFormat_HttpStatus400() {
		CreateUserRequest request = FakeUser.CreateRequest.withEmailInvalidFormat();
		ResponseEntity<?> response = postForEntity(request, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	private <T> ResponseEntity<T> postForEntity(CreateUserRequest request, Class<T> responseType) {
		HttpHeaders httpHeaders = accessTokenHeaders();
		return restTemplate.exchange("/v1/users", HttpMethod.POST,
				new HttpEntity<>(request, httpHeaders), responseType);
	}
	
	private HttpHeaders accessTokenHeaders() {
		String token = requestAccessToken();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		
		return headers;
	}
	
	private String requestAccessToken() {
		HttpHeaders tokenRequestHeaders = new HttpHeaders();
		tokenRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> tokenRequestBody = new HashMap<>();
		tokenRequestBody.put("client_id", config.getClientId());
		tokenRequestBody.put("client_secret", config.getClientSecret());
		tokenRequestBody.put("audience", config.getAudience());
		tokenRequestBody.put("grant_type", "client_credentials");
		
		ResponseEntity<Map> tokenResponse = restTemplate.exchange(config.getIssuer() + "/oauth/token",
				HttpMethod.POST, new HttpEntity<>(tokenRequestBody, tokenRequestHeaders), Map.class);
		return (String) tokenResponse.getBody().get("access_token");
	}
	
}
