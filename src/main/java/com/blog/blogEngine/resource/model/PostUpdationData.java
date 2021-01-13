package com.blog.blogEngine.resource.model;

public class PostUpdationData {

	private String userName;
	private String message;
	private String title;
	private String themeId;
	private String id;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getThemeId() {
		return themeId;
	}
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PostUpdationData() {
		
	}
	public PostUpdationData(String userName, String message, String title, String themeId, String id) {
		super();
		this.userName = userName;
		this.message = message;
		this.title = title;
		this.themeId = themeId;
		this.id = id;
	}
}
