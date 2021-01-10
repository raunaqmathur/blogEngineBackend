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

import com.blog.blogEngine.resource.model.GetPublishedPostData;
import com.blog.blogEngine.resource.model.PostCreationData;
import com.blog.blogEngine.resource.model.PostUpdationData;
import com.blog.blogEngine.response.exceptions.BlogNotFoundException;
import com.blog.blogEngine.response.exceptions.PostAlreadyPublishedException;
import com.blog.blogEngine.response.exceptions.PostCreationException;
import com.blog.blogEngine.response.exceptions.PostNotFoundException;
import com.blog.blogEngine.response.exceptions.PostUpdationException;
import com.blog.blogEngine.response.exceptions.UserNotFoundException;
import com.blog.blogEngine.response.model.ListPostResponse;
import com.blog.blogEngine.response.model.PostResponse;
import com.blog.blogEngine.service.PostService;
import com.blog.blogEngine.util.ErrorCode;

@Controller
public class PostResource {

	  @Autowired
	  PostService postService;
	
	  @GetMapping("/postByUser")
	  @ResponseBody
	  public ResponseEntity<Object> getAllPostByUser(@RequestParam(name = "userName", required = true) String userName) {
		  ListPostResponse lstPostResponse;
		try {
			lstPostResponse = postService.findByUser(userName);
			return new ResponseEntity<Object>(lstPostResponse, new HttpHeaders(), HttpStatus.ACCEPTED);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		  
	  }
	  
	  @PostMapping("/post/create")
	  @ResponseBody
	  public ResponseEntity<Object> createPost(@RequestBody PostCreationData postCreationData) {
		  try {
			  PostResponse postResponse = postService.insert(postCreationData.getMessage(), postCreationData.getTitle(),
					  postCreationData.getThemeId(), 
					  postCreationData.getUserName());
			  if(postResponse != null) {
				  return new ResponseEntity<Object>(postResponse, new HttpHeaders(), HttpStatus.ACCEPTED);
			  } else {
				  return new ResponseEntity<Object>(new PostCreationException("Error creating post", ErrorCode.POST_CREATION_ERROR), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			  }
		  } catch(UserNotFoundException e) {
			  return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		  } catch (PostCreationException e) {
			  return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BlogNotFoundException e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		  
	  }  
	  
	  @PostMapping("/post/update")
	  @ResponseBody
	  public ResponseEntity<Object> updatePost(@RequestBody PostUpdationData postUpdationData) {
		  try {
			  PostResponse postResponse = postService.update(postUpdationData.getMessage(), postUpdationData.getTitle(),
					  postUpdationData.getThemeId(), postUpdationData.getUserName(), postUpdationData.getId());
			  if(postResponse != null) {
				  return new ResponseEntity<Object>(postResponse, new HttpHeaders(), HttpStatus.ACCEPTED);
			  } else {
				  return new ResponseEntity<Object>(new PostCreationException("Error creating post", ErrorCode.POST_UPDATION_ERROR), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			  }
		  } catch(UserNotFoundException | BlogNotFoundException | PostNotFoundException | PostAlreadyPublishedException e) {
			  return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		  } catch (PostUpdationException e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	  }  
	  
	  @PostMapping("/post/delete")
	  @ResponseBody
	  public ResponseEntity<Object> deletePost(@RequestParam(name = "postId", required = true) String postId) {
		int returnCode = 0;
		try {
			returnCode = postService.delete(postId);
			if(returnCode == 0) {
				  return new ResponseEntity<Object>(new UserNotFoundException("post not found", ErrorCode.POST_NOT_FOUND), new HttpHeaders(), HttpStatus.BAD_REQUEST);
			  }
		      return new ResponseEntity<Object>("Post deleted", new HttpHeaders(), HttpStatus.ACCEPTED);
		} catch (PostUpdationException e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (PostNotFoundException e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	  }
	  
	  @PostMapping("/post/getAllpublished")
	  @ResponseBody
	  public ResponseEntity<Object> getPublishedPosts(@RequestBody GetPublishedPostData getPublishedPostData) {
		try {
			ListPostResponse lstPostResponse = postService.getPublishedPosts(getPublishedPostData.getStartDate(), getPublishedPostData.getEndDate());
		    return new ResponseEntity<Object>(lstPostResponse, new HttpHeaders(), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	  }
	  
	  @PostMapping("/post/getAllUnpublished")
	  @ResponseBody
	  public ResponseEntity<Object> getUnPublishedPosts(@RequestParam(name = "userName", required = true) String userName) {
		try {
			ListPostResponse lstPostResponse = postService.getUnPublishedPosts(userName);
		    return new ResponseEntity<Object>(lstPostResponse, new HttpHeaders(), HttpStatus.ACCEPTED);
		} catch(UserNotFoundException e) {
			  return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} 
	  }
	  
	  @PostMapping("/post/publish")
	  @ResponseBody
	  public ResponseEntity<Object> publishePost(@RequestParam(name = "postId", required = true) String postId) {
		int returnCode = 0;
		try {
			returnCode = postService.publish(postId);
			if(returnCode == 0) {
				  return new ResponseEntity<Object>(new UserNotFoundException("post not found", ErrorCode.POST_NOT_FOUND), new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		    return new ResponseEntity<Object>("Post published", new HttpHeaders(), HttpStatus.ACCEPTED);
		} catch (PostUpdationException e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (PostNotFoundException e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	  }
}
