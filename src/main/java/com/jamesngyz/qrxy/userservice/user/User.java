package com.jamesngyz.qrxy.userservice.user;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class User {
	
	@Id
	private UUID id;
	private String authId;
	private String username;
	private String email;
	private String bio;
	
	@CreatedDate
	private Date createdAt;
	
	@CreatedBy
	private String createdBy;
	
	@LastModifiedDate
	private Date updatedAt;
	
	@LastModifiedBy
	private String updatedBy;
	
}
