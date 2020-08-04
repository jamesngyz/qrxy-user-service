package com.jamesngyz.qrxy.userservice.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import com.jamesngyz.qrxy.userservice.IntegrationTestConfig;

@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserController_Update_IT {
	
	private final IntegrationTestConfig config;
	private final TestRestTemplate restTemplate;
	private final UserRepository repository;
	
	@Autowired
	UserController_Update_IT(
			IntegrationTestConfig config,
			TestRestTemplate restTemplate,
			UserRepository repository) {
		this.config = config;
		this.restTemplate = restTemplate;
		this.repository = repository;
	}
	
	@Test
	void updateUserAuthId_AllOk_HttpStatus200AndResponseBodyAuthIdAndPersisted() {
		HttpHeaders headers = accessTokenHeaders();
		
		CreateUserRequest createUserRequest = FakeUser.CreateRequest.withAuthIdNull();
		ResponseEntity<User> createResponse = restTemplate.exchange("/v1/users", HttpMethod.POST,
				new HttpEntity<>(createUserRequest, headers), User.class);
		
		UUID userId = createResponse.getBody().getId();
		String authId = UUID.randomUUID().toString();
		
		ResponseEntity<String> updateResponse = restTemplate.exchange("/v1/users/" + userId + "/auth_id",
				HttpMethod.PUT, new HttpEntity<>(authId, headers), String.class);
		
		assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(updateResponse.getBody()).isEqualTo(authId);
		assertThat(repository.findById(userId).get().getAuthId()).isEqualTo(authId);
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
