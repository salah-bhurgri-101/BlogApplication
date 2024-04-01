package com.blogapplication.services;

import java.util.List;

import com.blogapplication.payloads.UserDto;

public interface UserService {

	UserDto registrerNewUser(UserDto user);
	
	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);
}
