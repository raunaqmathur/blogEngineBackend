package com.blog.blogEngine.response.exceptions;

public class PostNotFoundException extends Exception {
	
	public int code;
	public PostNotFoundException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public PostNotFoundException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}
}
