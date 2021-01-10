package com.blog.blogEngine.response.exceptions;

public class BlogNotFoundException extends Exception {
	
	public int code;
	public BlogNotFoundException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public BlogNotFoundException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}
}
