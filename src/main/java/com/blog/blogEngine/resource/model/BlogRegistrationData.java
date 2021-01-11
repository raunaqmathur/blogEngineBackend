package com.blog.blogEngine.resource.model;

public class BlogRegistrationData {

	public String userName;
	public String website;
	public String name;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public BlogRegistrationData(String userName, String website, String name) {
		super();
		this.userName = userName;
		this.website = website;
		this.name = name;
	}
}
