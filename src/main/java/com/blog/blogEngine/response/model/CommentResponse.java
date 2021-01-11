package com.blog.blogEngine.response.model;

import java.util.Date;

public class CommentResponse {
	
	
	private String id;
	private UserResponse userResponse;
	private String message;
	private Date dateCreated;
	private int active;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UserResponse getUser() {
		return userResponse;
	}
	public void setUser(UserResponse userResponse) {
		this.userResponse = userResponse;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public CommentResponse(String id, UserResponse userResponse, String message, Date dateCreated, int active) {
		super();
		this.id = id;
		this.userResponse = userResponse;
		this.message = message;
		this.dateCreated = dateCreated;
		this.active = active;
	}
	@Override
	public String toString() {
		return "CommentResponse [id=" + id + ", userResponse=" + userResponse + ", message=" + message + ", dateCreated=" + dateCreated
				+ ", active=" + active + "]";
	}
}
