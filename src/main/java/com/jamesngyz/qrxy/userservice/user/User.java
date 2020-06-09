package com.jamesngyz.qrxy.userservice.user;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "person")
@Data
public class User {
	
	@Id
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "auth_id")
	private String authId;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "bio")
	private String bio = "";
	
	@Column(name = "created_at")
	@CreatedDate
	private Date createdAt;
	
	@Column(name = "created_by")
	@CreatedBy
	private String createdBy;
	
	@Column(name = "updated_at")
	@LastModifiedDate
	private Date updatedAt;
	
	@Column(name = "updated_by")
	@LastModifiedBy
	private String updatedBy;
	
}
