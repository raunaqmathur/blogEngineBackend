package com.blog.blogEngine.response.exceptions;

public class UserAlreadyExistsException extends Exception {
	
	public int code;
	public UserAlreadyExistsException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public UserAlreadyExistsException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}
}
