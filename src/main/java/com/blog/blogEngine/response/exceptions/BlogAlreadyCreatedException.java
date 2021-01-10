package com.blog.blogEngine.response.exceptions;

public class BlogAlreadyCreatedException extends Exception {
	
	public int code;
	public BlogAlreadyCreatedException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public BlogAlreadyCreatedException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}
}
