package com.jamesngyz.qrxy.userservice.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
class CreateUserRequest {
	
	@JsonProperty("auth_id")
	private String authId;
	
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("email")
	private String email;
	
}
