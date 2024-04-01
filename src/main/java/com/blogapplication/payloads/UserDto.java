package com.blogapplication.payloads;

import java.util.HashSet;
import java.util.Set;

import com.blogapplication.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	@NotEmpty
	@Size(min = 4, message = "Username must be min 4 characters !!")
	private String name;
	@Email(regexp = "^(?=.*?[@]).{15,}$", message = "Email address is not valid !!")
	private String email;
	@NotEmpty(message = "Password is required  !!")
//	@Size(min = 3, max =10, message ="Password must be min of 3 chars  and max of 10 chars") because regexp me min 8 or max is 10 .{8,10}
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,10}$", message = "Please use at least one uppercase letter, one lowercase letter, one digit, and one special character min 8 or max is 10.")
	private String password;
	@NotEmpty
	private String about;

	private Set<RoleDto> roles = new HashSet<>();

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
}
