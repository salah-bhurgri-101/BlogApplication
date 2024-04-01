package com.blogapplication;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogapplication.config.AppConstants;
import com.blogapplication.entities.Role;
import com.blogapplication.repositories.RoleRepo;
import com.blogapplication.security.CustomUserDetailService;

import jakarta.validation.constraints.AssertFalse.List;

@SpringBootApplication
public class BlogApllicationApiApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApllicationApiApplication.class, args);
		System.out.println("******Started******");
	}

	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(this.passwordEncoder.encode("salah"));
		
		try {
			
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("NORMAL_USER");
		 
			java.util.List<Role> roles = new ArrayList<>();
			roles.add(role);
			roles.add(role2);
			
			java.util.List<Role> saveAll = roleRepo.saveAll(roles);
			
			saveAll.forEach(r->{
				System.out.println(r.getName());
			});
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		
	}
	
}
