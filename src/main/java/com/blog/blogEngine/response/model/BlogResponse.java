package com.blog.blogEngine.response.model;

import java.util.Date;

import com.blog.blogEngine.model.User;

public class BlogResponse {

	private UserResponse userResponse;
	private String website;
	private String name;
	private Date dateCreated;
	private int active;
	
	public UserResponse getUserResponse() {
		return userResponse;
	}
	public void setUserResponse(UserResponse UserResponse) {
		this.userResponse = UserResponse;
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
	
	public BlogResponse(User user, String website, String name, Date dateCreated, int active) {
		super();
		this.userResponse = new UserResponse(user.getFirstName(),
				user.getLastName(),
				user.getUserName(),
				user.getEmail(),
				user.getDateCreated(),
				user.getActive());
		this.website = website;
		this.name = name;
		this.dateCreated = dateCreated;
		this.active = active;
	}
	@Override
	public String toString() {
		return "BlogResponse [UserResponse=" + userResponse + ", website=" + website + ", name=" + name + ", dateCreated=" + dateCreated
				+ ", active=" + active + "]";
	}
}
