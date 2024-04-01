package com.blogapplication.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String recourceName;
	String fieldName;
	long fieldValue;

	public ResourceNotFoundException(String recourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", recourceName, fieldName, fieldValue));
		this.recourceName = recourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

}
