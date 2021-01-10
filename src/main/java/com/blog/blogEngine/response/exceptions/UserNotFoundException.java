package com.blog.blogEngine.response.exceptions;

public class UserNotFoundException extends Exception {
	
	public int code;
	public UserNotFoundException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public UserNotFoundException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}
}
