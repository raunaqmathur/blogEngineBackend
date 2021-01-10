package com.blog.blogEngine.response.exceptions;

public class PostAlreadyPublishedException extends Exception {
	
	public int code;
	public PostAlreadyPublishedException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public PostAlreadyPublishedException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}
}
