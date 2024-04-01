package com.blogapplication.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.entities.User;
import com.blogapplication.exceptions.ApiExceptioin;
import com.blogapplication.payloads.JwtAuthRequest;
import com.blogapplication.payloads.JwtAuthResponse;
import com.blogapplication.payloads.UserDto;
import com.blogapplication.repositories.UserRepo;
import com.blogapplication.security.JwtTokenHelper;
import com.blogapplication.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	//extra
//	@Autowired
//	private UserRepo userRepo;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception{
		
		authenticate(jwtAuthRequest.getUsername() , jwtAuthRequest.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		
		String token = jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		
		jwtAuthResponse.setToken(token);
		jwtAuthResponse.setUserDto(mapper.map((User)userDetails,UserDto.class));
		//extra
//		User user2 = userRepo.findByEmail(jwtAuthRequest.getUsername()).get();
//		System.err.println("User 2 " + user2);
//		UserDto user = userService.getUserById(user2.getId());
//		jwtAuthResponse.setId(user2.getId());
		
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);	
	}
	
	private void authenticate(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
//		BasicAuthenticationFilter usernamePasswordAuthenticationToken = new BasicAuthenticationFilter(authenticationManager); 
		try {
			authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			// TODO: handle exception
			System.out.println("Invalid Details !!");
			throw new ApiExceptioin("Invalid Username or password !!");
		}
	
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser (@Valid @RequestBody UserDto userDto){
		UserDto registerUser = userService.registrerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registerUser,HttpStatus.CREATED);
	}
	
}
