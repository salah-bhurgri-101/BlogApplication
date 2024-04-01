package com.blogapplication.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapplication.config.AppConstants;
import com.blogapplication.entities.Role;
import com.blogapplication.entities.User;
import com.blogapplication.exceptions.ResourceNotFoundException;
import com.blogapplication.payloads.UserDto;
import com.blogapplication.repositories.RoleRepo;
import com.blogapplication.repositories.UserRepo;
import com.blogapplication.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.dtoToUser(userDto);
		User savedUser = userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));

		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());

		User updatedUser = userRepo.save(user);
		UserDto userToDto1 = this.userToDto(updatedUser);
		return userToDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "Id", userId));
		userRepo.delete(user);
		
	}

	public User dtoToUser(UserDto userDto) {
		
		User user = modelMapper.map(userDto, User.class);
		
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());

		return user;
	}

	public UserDto userToDto(User user) {
		
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());

		return userDto;
	}

	@Override
	public UserDto registrerNewUser(UserDto userDto) {
		// TODO Auto-generated method stub
		
		User user = dtoToUser(userDto);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//Role
		
		//Role role = roleRepo.findById(AppConstants.NORMAL_USER).get();
		Role role = roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User savedUser = userRepo.save(user);
		
		UserDto userToDto = userToDto(savedUser);
		
		return userToDto;
	}

}
