package com.blogapplication.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	
	private UserDto userDto;
	//extra
//	private int id;
}
