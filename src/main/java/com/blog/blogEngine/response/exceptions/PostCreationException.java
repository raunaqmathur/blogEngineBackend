package com.blog.blogEngine.response.exceptions;

public class PostCreationException extends Exception {
	
	public int code;
	public PostCreationException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public PostCreationException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}
}
