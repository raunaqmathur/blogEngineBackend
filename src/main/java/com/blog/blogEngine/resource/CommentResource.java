package com.blog.blogEngine.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.blogEngine.resource.model.CommentCreationData;
import com.blog.blogEngine.resource.model.GetPublishedPostData;
import com.blog.blogEngine.resource.model.PostCreationData;
import com.blog.blogEngine.resource.model.PostUpdationData;
import com.blog.blogEngine.response.exceptions.BlogCreationException;
import com.blog.blogEngine.response.exceptions.BlogNotFoundException;
import com.blog.blogEngine.response.exceptions.CommentCreationException;
import com.blog.blogEngine.response.exceptions.PostAlreadyPublishedException;
import com.blog.blogEngine.response.exceptions.PostCreationException;
import com.blog.blogEngine.response.exceptions.PostNotFoundException;
import com.blog.blogEngine.response.exceptions.PostUpdationException;
import com.blog.blogEngine.response.exceptions.UserNotFoundException;
import com.blog.blogEngine.response.model.CommentResponse;
import com.blog.blogEngine.response.model.ListPostResponse;
import com.blog.blogEngine.response.model.PostResponse;
import com.blog.blogEngine.service.CommentService;
import com.blog.blogEngine.service.PostService;
import com.blog.blogEngine.util.ErrorCode;

@Controller
public class CommentResource {

	  @Autowired
	  CommentService commentService;
	
	  /**
	   * This method is to get all comments for a post.
	   * @param postId This is the first parameter to getAllCommentsForPost method
	   * @return ResponseEntity<Object> This returns list of comments, header and HttpStatus.
	   */
	  @GetMapping("/comment/getAllForPost")
	  @ResponseBody
	  public ResponseEntity<Object> getAllCommentsForPost(@RequestParam(name = "postId", required = true) String postId) {
		  List<CommentResponse> lstCommentResponse;
		try {
			lstCommentResponse = commentService.getAllCommentsForPost(postId);
			return new ResponseEntity<Object>(lstCommentResponse, new HttpHeaders(), HttpStatus.ACCEPTED);
		} catch (PostNotFoundException e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} 
		  
	  }
	  
	  /**
	   * This method is to create a comment on a post.
	   * @param CommentCreationData This is the first parameter to createPost method
	   * @return ResponseEntity<Object> This returns list of comment object, header and HttpStatus.
	   */
	  @PostMapping("/comment/create")
	  @ResponseBody
	  public ResponseEntity<Object> createPost(@RequestBody CommentCreationData commentCreationData) {
		  try {
			  CommentResponse commentResponse = commentService.insert(commentCreationData.getMessage(),
					  commentCreationData.getUserName(),
					  commentCreationData.getPostId());
			  if(commentResponse != null) {
				  return new ResponseEntity<Object>(commentResponse, new HttpHeaders(), HttpStatus.CREATED);
			  } else {
				  return new ResponseEntity<Object>(new PostCreationException("Error creating post", ErrorCode.POST_CREATION_ERROR), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			  }
		  } catch(UserNotFoundException | PostNotFoundException e) {
			  return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		  }  catch (CommentCreationException e) {
			  return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		  
	  }  
}
