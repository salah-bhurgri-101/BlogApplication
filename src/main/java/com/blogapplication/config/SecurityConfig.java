package com.blogapplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.blogapplication.security.CustomUserDetailService;
import com.blogapplication.security.JwtAuthenticationEntryPoint;
import com.blogapplication.security.JwtAuthenticationFilter;

import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	public static final String[] PUBLIC_URL = {
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
//	@Bean
//	public UserDetailsService  getUserDetailsService() {
//		return new CustomUserDetailService();
//	}
	
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(this.customUserDetailService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return authenticationProvider;
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		/*
		 * http .authorizeRequests() .requestMatchers("/", "/home").permitAll()
		 * .anyRequest().authenticated() .and() .formLogin() .loginPage("/login")
		 * .permitAll() .and() .logout() .permitAll();
		 */
	http.csrf()
	.disable()
	.authorizeHttpRequests()
	.requestMatchers(PUBLIC_URL).permitAll()
//	.requestMatchers("/api/v1/auth/**").permitAll()
//	.requestMatchers("/v3/api-docs").permitAll()
//	.requestMatchers("/api/v1/auth/login").permitAll()
	.requestMatchers(HttpMethod.GET).permitAll() // allow to any role to get all get mapping
	.anyRequest()
	.authenticated()
	.and()
	.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
	.and()
	.sessionManagement()
	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
//	http.addFilterBefore(jwtAuthenticationFilter, (Class<? extends Filter>) UsernamePasswordAuthenticationToken.class); 
	//http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationToken.class);
		
	http.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class); 
		
		http.authenticationProvider(daoAuthenticationProvider());
		DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
		return defaultSecurityFilterChain;
	}
	
	
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
	}
	
	//Croos Origin
	
	
	@Bean
	public FilterRegistrationBean coresFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedHeader("Authorization");
		corsConfiguration.addAllowedHeader("Content-type");
		corsConfiguration.addAllowedHeader("Accept");
		corsConfiguration.addAllowedMethod("POST");
		corsConfiguration.addAllowedMethod("GET");
		corsConfiguration.addAllowedMethod("DELETE");
		corsConfiguration.addAllowedMethod("PUT");
		corsConfiguration.addAllowedMethod("OPTIONS");
		corsConfiguration.setMaxAge(3600L);
		
		source.registerCorsConfiguration("/**", corsConfiguration);
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new CorsFilter(source));
		filterRegistrationBean.setOrder(-110);
		return filterRegistrationBean;
	}
	
}
