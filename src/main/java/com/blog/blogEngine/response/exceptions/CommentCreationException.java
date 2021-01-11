package com.blog.blogEngine.response.exceptions;

public class CommentCreationException extends Exception {
	
	public int code;
	public CommentCreationException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public CommentCreationException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}
}
