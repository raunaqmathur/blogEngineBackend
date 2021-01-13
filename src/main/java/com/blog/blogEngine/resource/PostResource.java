package com.blog.blogEngine.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
public class PostResource {
	  private static final Logger logger = LogManager.getLogger(PostResource.class);
	  
	  @Autowired
	  PostService postService;
	
	  /**
	   * This method is to get all published posts by user.
	   * @param userName This is the first parameter to getAllPublishedPostByUser method
	   * @return ResponseEntity<Object> This returns list of posts, header and HttpStatus.
	   */
	  @GetMapping("/post/user/getAllpublished")
	  @ResponseBody
	  public ResponseEntity<Object> getAllPublishedPostByUser(@RequestParam(name = "userName", required = true) String userName) {
		  ListPostResponse lstPostResponse;
		try {
			lstPostResponse = postService.getAllPublishedPostByUser(userName);
			return new ResponseEntity<Object>(lstPostResponse, new HttpHeaders(), HttpStatus.ACCEPTED);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		  
	  }
	  
	  /**
	   * This method is create Post.
	   * @param PostCreationData This is the first parameter to createPost method
	   * @return ResponseEntity<Object> This returns post object, header and HttpStatus.
	   */
	  @PostMapping("/post/create")
	  @ResponseBody
	  public ResponseEntity<Object> createPost(@RequestBody PostCreationData postCreationData) {
		  logger.info("postCreationData: " + postCreationData.getMessage() + ", " + postCreationData.getTitle()+ ", " + 
				  postCreationData.getThemeId() + ", " + 
				  postCreationData.getUserName());
		  try {
			  PostResponse postResponse = postService.insert(postCreationData.getMessage(), postCreationData.getTitle(),
					  postCreationData.getThemeId(), 
					  postCreationData.getUserName());
			  if(postResponse != null) {
				  return new ResponseEntity<Object>(postResponse, new HttpHeaders(), HttpStatus.CREATED);
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
	  
	  /**
	   * This method is update Post.
	   * @param PostUpdationData This is the first parameter to updatePost method
	   * @return ResponseEntity<Object> This returns post object, header and HttpStatus.
	   */
	  @GetMapping("/post/getPost")
	  @ResponseBody
	  public ResponseEntity<Object> getPost(@RequestParam(name = "postId", required = true) String postId) {
		  try {
			  PostResponse postResponse = postService.getPost(postId);
			  if(postResponse != null) {
				  return new ResponseEntity<Object>(postResponse, new HttpHeaders(), HttpStatus.ACCEPTED);
			  } else {
				  return new ResponseEntity<Object>(new PostCreationException("Error creating post", ErrorCode.POST_UPDATION_ERROR), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			  }
		  } catch(PostNotFoundException e) {
			  return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		  } catch (PostUpdationException e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	  }  
	  
	  /**
	   * This method is update Post.
	   * @param PostUpdationData This is the first parameter to updatePost method
	   * @return ResponseEntity<Object> This returns post object, header and HttpStatus.
	   */
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
	  
	  /**
	   * This method is delete Post.
	   * @param postId This is the first parameter to deletePost method
	   * @return ResponseEntity<Object> This returns String, header and HttpStatus.
	   */
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
	  
	  /**
	   * This method is to get published posts between start and end date.
	   * @param GetPublishedPostData This is the first parameter to getPublishedPostsWithDate method
	   * @return ResponseEntity<Object> This returns list of posts, header and HttpStatus.
	   */
	  @PostMapping("/post/getAllpublishedWithDate")
	  @ResponseBody
	  public ResponseEntity<Object> getPublishedPostsWithDate(@RequestBody GetPublishedPostData getPublishedPostData) {
		logger.info("called getPublishedPosts with: " + getPublishedPostData.getStartDate() + ",  " + getPublishedPostData.getEndDate());
		try {
			ListPostResponse lstPostResponse = postService.getPublishedPostsWithDate(getPublishedPostData.getStartDate(), getPublishedPostData.getEndDate());
		    return new ResponseEntity<Object>(lstPostResponse, new HttpHeaders(), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	  }
	  
	  /**
	   * This method is to get published posts .
	   * @return ResponseEntity<Object> This returns list of posts, header and HttpStatus.
	   */
	  @PostMapping("/post/getAllpublished")
	  @ResponseBody
	  public ResponseEntity<Object> getPublishedPosts() {
		logger.info("called getPublishedPosts ");
		try {
			ListPostResponse lstPostResponse = postService.getPublishedPosts();
		    return new ResponseEntity<Object>(lstPostResponse, new HttpHeaders(), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	  }
	  
	  /**
	   * This method is to get all unpublished posts by user.
	   * @param userName This is the first parameter to getUnPublishedPosts method
	   * @return ResponseEntity<Object> This returns list of posts, header and HttpStatus.
	   */
	  @GetMapping("/post/getAllUnpublished")
	  @ResponseBody
	  public ResponseEntity<Object> getUnPublishedPosts(@RequestParam(name = "userName", required = true) String userName) {
		try {
			ListPostResponse lstPostResponse = postService.getUnPublishedPosts(userName);
		    return new ResponseEntity<Object>(lstPostResponse, new HttpHeaders(), HttpStatus.ACCEPTED);
		} catch(UserNotFoundException e) {
			  return new ResponseEntity<Object>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} 
	  }
	  
	  /**
	   * This method is to publish post.
	   * @param postId This is the first parameter to publishPost method
	   * @return ResponseEntity<Object> This returns String, header and HttpStatus.
	   */
	  @PostMapping("/post/publish")
	  @ResponseBody
	  public ResponseEntity<Object> publishPost(@RequestParam(name = "postId", required = true) String postId) {
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
