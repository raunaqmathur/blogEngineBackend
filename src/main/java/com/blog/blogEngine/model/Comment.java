package com.blog.blogEngine.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@SuppressWarnings("serial")
@Document(collection = "Comment")
public class Comment{
	
	@Id
	private String id;
	
	@DBRef
	private User user;
	@DBRef
	private Post post;
	private String message;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dateCreated;
	private int active;
	public String getId() {
		return id;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
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

	public Comment(User user, Post post, String message, Date dateCreated, int active) {
		super();
		this.user = user;
		this.post = post;
		this.message = message;
		this.dateCreated = dateCreated;
		this.active = active;
	}

	@Override
	public String toString() {
		return "Comment [user=" + user + ", post=" + post + ", message=" + message + ", dateCreated=" + dateCreated
				+ ", active=" + active + "]";
	}
}
