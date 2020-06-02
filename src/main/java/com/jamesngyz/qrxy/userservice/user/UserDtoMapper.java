package com.jamesngyz.qrxy.userservice.user;

import org.mapstruct.Mapper;

@Mapper
public interface UserDtoMapper {
	
	UserResponse userToResponse(User user);
	
	User createRequestToUser(CreateUserRequest request);
	
}
