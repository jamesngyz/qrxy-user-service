package com.jamesngyz.qrxy.userservice.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
class CreateUserRequest {
	
	@JsonProperty("auth_id")
	private String authId;
	
	@JsonProperty("username")
	@NotNull
	@Size(min = 3, max = 20)
	@Pattern(regexp = "^[a-z0-9]*$")
	private String username;
	
	@JsonProperty("email")
	private String email;
	
}
