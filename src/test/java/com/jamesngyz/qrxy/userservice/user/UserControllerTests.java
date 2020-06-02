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
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class UserControllerTests {
	
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
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
	
}
