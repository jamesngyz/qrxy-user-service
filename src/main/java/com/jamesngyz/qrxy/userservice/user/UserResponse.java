package com.jamesngyz.qrxy.userservice.user;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
class UserResponse {
	
	@JsonProperty("id")
	private UUID id;
	
	@JsonProperty("auth_id")
	private String authId;
	
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("bio")
	private String bio;
	
	@JsonProperty("metadata")
	private Object metadata;
	
	@JsonProperty("created_at")
	private Date createdAt;
	
	@JsonProperty("created_by")
	private String createdBy;
	
	@JsonProperty("updated_at")
	private Date updatedAt;
	
	@JsonProperty("updated_by")
	private String updatedBy;
	
}
