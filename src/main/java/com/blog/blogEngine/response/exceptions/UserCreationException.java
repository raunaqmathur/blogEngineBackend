package com.blog.blogEngine.response.exceptions;

public class UserCreationException extends Exception {
	
	public int code;
	public UserCreationException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public UserCreationException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}
}
