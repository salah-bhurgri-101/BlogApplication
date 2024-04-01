package com.blogapplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blogapplication.repositories.UserRepo;

@SpringBootTest
class BlogApllicationApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private UserRepo repo;
	@Test
	public void userRepo() {
		String className = repo.getClass().getName();
		String packageName = repo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packageName);
	}
}
