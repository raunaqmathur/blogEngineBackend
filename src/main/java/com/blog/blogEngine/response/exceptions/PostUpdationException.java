package com.blog.blogEngine.response.exceptions;

public class PostUpdationException extends Exception {
	
	public int code;
	public PostUpdationException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public PostUpdationException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}
}
