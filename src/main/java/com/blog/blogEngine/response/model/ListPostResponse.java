package com.blog.blogEngine.response.model;

import java.util.List;

public class ListPostResponse {
	private List<PostResponse> lstPostResponse;
	private UserResponse userResponse;
	private BlogResponse blogResponse;
	
	public List<PostResponse> getLstPostResponse() {
		return lstPostResponse;
	}
	public void setLstPostResponse(List<PostResponse> lstPostResponse) {
		this.lstPostResponse = lstPostResponse;
	}
	public UserResponse getUserResponse() {
		return userResponse;
	}
	public void setUserResponse(UserResponse userResponse) {
		this.userResponse = userResponse;
	}
	public BlogResponse getBlogResponse() {
		return blogResponse;
	}
	public void setBlogResponse(BlogResponse blogResponse) {
		this.blogResponse = blogResponse;
	}
	
	public ListPostResponse(List<PostResponse> lstPostResponse, UserResponse userResponse, BlogResponse blogResponse) {
		super();
		this.lstPostResponse = lstPostResponse;
		this.userResponse = userResponse;
		this.blogResponse = blogResponse;
	}
}
