package com.jamesngyz.qrxy.userservice.user;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@WebMvcTest
public class UserControllerTests {
	
	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	
	@MockBean
	private UserService service;
	
	@Autowired
	public UserControllerTests(
			MockMvc mockMvc,
			ObjectMapper objectMapper) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
	}
	
	@Test
	void createUser_AllOk_HttpStatus201() throws Exception {
		
		CreateUserRequest request = FakeUser.CreateRequest.build();
		String requestJson = objectMapper.writeValueAsString(request);
		
		User user = FakeUser.fromRequestAndSetSystemGeneratedFields(request);
		when(service.createUser(notNull())).thenReturn(user);
		
		UserResponse response = FakeUser.Response.fromUser(user);
		String expected = objectMapper.writeValueAsString(response);
		
		mockMvc.perform(
				post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(header().string("location", user.getId().toString()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(expected));
	}
	
	@Test
	void createUser_AuthIdNull_HttpStatus400() throws Exception {
		CreateUserRequest request = FakeUser.CreateRequest.withAuthIdNull();
		String requestJson = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void createUser_UsernameNull_HttpStatus400() throws Exception {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameNull();
		String requestJson = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void createUser_UsernameShorterThan3_HttpStatus400() throws Exception {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameShorterThan3();
		String requestJson = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void createUser_UsernameLongerThan20_HttpStatus400() throws Exception {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameLongerThan20();
		String requestJson = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void createUser_UsernameNotAlphanumeric_HttpStatus400() throws Exception {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameNotAlphanumeric();
		String requestJson = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void createUser_UsernameNotLowerCase_HttpStatus400() throws Exception {
		CreateUserRequest request = FakeUser.CreateRequest.withUsernameNotLowerCase();
		String requestJson = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void createUser_EmailNull_HttpStatus400() throws Exception {
		CreateUserRequest request = FakeUser.CreateRequest.withEmailNull();
		String requestJson = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void createUser_EmailInvalidFormat_HttpStatus400() throws Exception {
		CreateUserRequest request = FakeUser.CreateRequest.withEmailInvalidFormat();
		String requestJson = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
}
