package com.blogapplication.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_12).apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	public ApiInfo getInfo() {
		return new ApiInfo("Blogging Application :Backend Course" ,"This project is developed by salah bhurgri" , "1.0" ,"Terms os service" , new Contact("Salah" ,"http://salahbhurgri","salah@Gmail.com") ,"License of APIS" ,"Api License URl" ,Collections.emptyList());
	}
}
