package com.blog.blogEngine.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@SuppressWarnings("serial")
@Document(collection = "Post")
public class Post implements Comparable<Post>{
	
	@Id
	private String id;
	
	@DBRef
	private User user;
	
	@DBRef
	private Blog blog;
	
	private String message;
	private String title;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dateCreated;
	
	private boolean published;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date datePublished;
	
	private String themeId;
	
	private int active;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	
	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	
	public String getId() {
		return id;
	}

	public Post(User user, Blog blog, String message, String title, String themeId, Date dateCreated, int active) {
		super();
		this.user = user;
		this.blog = blog;
		this.message = message;
		this.title = title;
		this.dateCreated = dateCreated;
		this.active = active;
		this.themeId = themeId;
	}

	@Override
	public String toString() {
		return "Post [user=" + user + ", blog=" + blog + ", message=" + message + ", title=" + title + ", dateCreated="
				+ dateCreated + ", published=" + published + ", datePublished=" + datePublished + ", themeId=" + themeId
				+ ", active=" + active + "]";
	}

	@Override
	public int compareTo(Post p) {
		return this.getDatePublished().before(p.getDatePublished()) ? 1 : 0;
	}

}
