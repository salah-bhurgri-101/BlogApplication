package com.blogapplication.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.exceptions.HttpRequestMethodNotSupportedException;
import com.blogapplication.payloads.ApiResponse;
import com.blogapplication.payloads.UserDto;
import com.blogapplication.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	//POST -create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		
		UserDto createUser = userService.createUser(userDto);
		//return new ResponseEntity<>(createUser,HttpStatus.CREATED);
		return new ResponseEntity<UserDto>(createUser,HttpStatus.CREATED);
	}
	
	
	//PUT -update users
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto , @PathVariable("userId") Integer uid){
		UserDto updatedUser = userService.updateUser(userDto, uid);
		return ResponseEntity.ok(updatedUser);
	}
	
	//Delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		userService.deleteUser(uid);
        //return new ResponseEntity(Map.of("message" , "User Deleted Succesfully"), HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully" , true), HttpStatus.OK);
	}
	
    //	GET -user get
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	//	GET -user get
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(userService.getUserById(userId));
	}
	
	
	//dfsdf
	@PutMapping("/")
	public HttpRequestMethodNotSupportedException updateUrlnotFound(){
		throw new HttpRequestMethodNotSupportedException("url");
		
		
	}
	@DeleteMapping("/")
	public HttpRequestMethodNotSupportedException deleteUrlNotFound(){
		throw new HttpRequestMethodNotSupportedException("url");
		
		
	}
	
}
