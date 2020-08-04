package com.jamesngyz.qrxy.userservice.user;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest
public class UserController_Update_Tests {
	
	private final MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	UserController_Update_Tests(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
	
	@Test
	void addUserAuthId_AllOk_HttpStatus200AndAuthId() throws Exception {
		
		UUID userId = UUID.randomUUID();
		String authId = UUID.randomUUID().toString();
		
		when(userService.updateAuthId(userId, authId)).thenReturn(authId);
		
		mockMvc.perform(
				put("/v1/users/" + userId + "/auth_id")
						.contentType(MediaType.TEXT_PLAIN_VALUE)
						.content(authId))
				.andExpect(status().isOk())
				.andExpect(content().string(authId));
	}
}
