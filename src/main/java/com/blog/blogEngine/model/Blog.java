package com.blog.blogEngine.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@SuppressWarnings("serial")
@Document(collection = "Blog")
public class Blog {
	
	@Id
	private String id;
	
	@DBRef
	private User user;
	
	private String website;
	private String name;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dateCreated;
	
	private int active;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Blog(User user, String website, String name, Date dateCreated, int active) {
		super();
		this.user = user;
		this.website = website;
		this.name = name;
		this.dateCreated = dateCreated;
		this.active = active;
	}

	@Override
	public String toString() {
		return "Blog [user=" + user + ", website=" + website + ", name=" + name + ", dateCreated="
				+ dateCreated + ", active=" + active + "]";
	}
	
	
}
