package com.blogapplication.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	
		// 1.get token
		String requestToken = request.getHeader("Authorization");
		
		//bearer 23423423dsds
		
		System.out.println("token "+requestToken);
		
		String username = null;
		String token = null;
		
		if (requestToken!=null && requestToken.startsWith("Bearer")) {
			
			token= requestToken.substring(7);
			
			try {
				
				 username = jwtTokenHelper.getUsernameFromToken(token);
				
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				System.out.println("Unable to get jwt token");
			}catch (ExpiredJwtException e) {
				// TODO: handle exception
				System.out.println("Jwt token has expired");
			}catch (MalformedJwtException e) {
				// TODO: handle exception
				System.err.println("invalid jwt");
			}
			
			
		}else {
			
			System.out.println("Jwt token dose not begin with Bearer");
		}
		
		//validate
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			if(jwtTokenHelper.validateToken(token, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				
			}else {
				System.out.println("Invalid jwt token");
			}
			
		}else {
			System.err.println("username is null  or context is not null");
		}

		//filterchain
		
		filterChain.doFilter(request, response);
		
	}

}
