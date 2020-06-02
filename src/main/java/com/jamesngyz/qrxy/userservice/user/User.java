package com.jamesngyz.qrxy.userservice.user;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	private UUID id;
	private String authId;
	private String username;
	private String email;
	private String bio;
	private Date createdAt;
	private String createdBy;
	private Date updatedAt;
	private String updatedBy;
	
}
