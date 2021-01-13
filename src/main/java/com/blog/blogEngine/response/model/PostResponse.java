package com.blog.blogEngine.response.model;

import java.util.Date;

public class PostResponse {
	private String message;
	private String title;
	private Date dateCreated;
	private String id;
	private Date datePublished;
	private boolean published;
	private int active;
	private UserResponse userResponse;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getDatePublished() {
		return datePublished;
	}
	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}	
	public UserResponse getUserResponse() {
		return userResponse;
	}
	public void setUserResponse(UserResponse userResponse) {
		this.userResponse = userResponse;
	}
	
	public PostResponse(String message, String title, Date dateCreated, String id, Date datePublished,
			boolean published, int active, UserResponse userResponse) {
		super();
		this.message = message;
		this.title = title;
		this.dateCreated = dateCreated;
		this.id = id;
		this.datePublished = datePublished;
		this.published = published;
		this.active = active;
		this.userResponse = userResponse;
	}
	
	
}
