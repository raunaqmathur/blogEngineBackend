package com.blog.blogEngine.util;

import java.util.List;

import com.blog.blogEngine.model.Blog;
import com.blog.blogEngine.model.Comment;
import com.blog.blogEngine.model.Post;
import com.blog.blogEngine.model.User;
import com.blog.blogEngine.response.model.BlogResponse;
import com.blog.blogEngine.response.model.CommentResponse;
import com.blog.blogEngine.response.model.ListPostResponse;
import com.blog.blogEngine.response.model.PostResponse;
import com.blog.blogEngine.response.model.UserResponse;

public class ModelConvertor {

	public static UserResponse userToUserResponseConvertor(User user) {
		return new UserResponse(user.getFirstName(),
				user.getLastName(),
				user.getUserName(),
				user.getEmail(),
				user.getDateCreated(),
				user.getActive());
	}
	
	public static BlogResponse blogToBlogResponseConvertor(Blog blog) {
		return new BlogResponse(blog.getUser(),
				blog.getWebsite(),
				blog.getName(),
				blog.getDateCreated(),
				blog.getActive());
	}
	
	public static PostResponse postToPostResponseConvertor(Post post) {
		return new PostResponse(post.getMessage(),
				post.getTitle(),
				post.getDateCreated(),
				post.getId(),
				post.getDatePublished(),
				post.isPublished(),
				post.getActive(),
				userToUserResponseConvertor(post.getUser()));
	}
	
	public static ListPostResponse postToListPostResponseConvertor(List<PostResponse> lstPostResponse, 
			BlogResponse blogResponse, UserResponse userResponse) {
		return new ListPostResponse(lstPostResponse, userResponse, blogResponse);
	}
	
	public static CommentResponse commentToCommentResponseConvertor(Comment comment) {
		return new CommentResponse(comment.getId(),
				userToUserResponseConvertor(comment.getUser()),
				comment.getMessage(),
				comment.getDateCreated(),
				comment.getActive());
	}
}
