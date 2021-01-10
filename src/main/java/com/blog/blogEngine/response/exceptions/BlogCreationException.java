package com.blog.blogEngine.response.exceptions;

public class BlogCreationException extends Exception {
	
	public int code;
	public BlogCreationException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public BlogCreationException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}
}
