package com.blogapplication.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpRequestMethodNotSupportedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String url;
	

	public HttpRequestMethodNotSupportedException(String url) {
		super(String.format("%s not found", url));
		this.url = url;
		
	}

}
