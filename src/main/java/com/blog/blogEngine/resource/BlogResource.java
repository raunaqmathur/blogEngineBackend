package com.blog.blogEngine.resource;

import java.util.List;

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

import com.blog.blogEngine.resource.model.BlogRegistrationData;
import com.blog.blogEngine.response.exceptions.BlogAlreadyCreatedException;
import com.blog.blogEngine.response.exceptions.BlogCreationException;
import com.blog.blogEngine.response.exceptions.BlogNotFoundException;
import com.blog.blogEngine.response.exceptions.UserNotFoundException;
import com.blog.blogEngine.response.model.BlogResponse;
import com.blog.blogEngine.service.BlogService;
import com.blog.blogEngine.util.ErrorCode;

@Controller
@CrossOrigin(origins = "*")
public class BlogResource {
	  
	  @Autowired
	  BlogService blogService;
	
	  /**
	   * This method is fetch blog details based on userName provided.
	   * @param userName This is the first paramter to deleteAccount method
	   * @return ResponseEntity<Object> This returns blog object, header and HttpStatus.
	   */
	  @GetMapping("/blog")
	  @ResponseBody
	  public ResponseEntity<Object> getBlogByUser(@RequestParam(name = "userName", required = true) String userName) {
		  BlogResponse blogResonse;
		try {
			blogResonse = blogService.findByUser(userName);
			return new ResponseEntity<Object>(blogResonse, new HttpHeaders(), HttpStatus.ACCEPTED);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<Object>(new UserNotFoundException("user not found", ErrorCode.USER_NOT_FOUND), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (BlogNotFoundException e) {
			return new ResponseEntity<Object>(new BlogNotFoundException("blog not found", 
					  ErrorCode.BLOG_NOT_FOUND), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		  
	  }
	  
	  /**
	   * This method is fetch all blogs details.
	   * @return ResponseEntity<Object> This returns list of blogs, header and HttpStatus.
	   */
	  @GetMapping("/blogs")
	  @ResponseBody
	  public ResponseEntity<Object> getAllBlogs() {
		  List<BlogResponse> blogResonse;
		  blogResonse = blogService.findAll();
		  return new ResponseEntity<Object>(blogResonse, new HttpHeaders(), HttpStatus.ACCEPTED);
	  }
	  
	  /**
	   * This method is create blog.
	   * @param BlogRegistrationData This is the first paramter to deleteAccount method
	   * @return ResponseEntity<Object> This returns blog object, header and HttpStatus.
	   */
	  @PostMapping("/blog/create")
	  @ResponseBody
	  public ResponseEntity<Object> register(@RequestBody BlogRegistrationData blogRegistrationData) {
		  try {
			  BlogResponse blogResponse = blogService.insert(blogRegistrationData.getWebsite(), blogRegistrationData.getName(), 
					  blogRegistrationData.getUserName());
			  if(blogResponse != null) {
				  return new ResponseEntity<Object>(blogResponse, new HttpHeaders(), HttpStatus.ACCEPTED);
			  } else {
				  return new ResponseEntity<Object>(new BlogCreationException("Error creating blog", ErrorCode.BLOG_CREATION_ERROR), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			  }
		  } catch(UserNotFoundException ex) {
			  return new ResponseEntity<Object>(new UserNotFoundException("user not found", ErrorCode.USER_NOT_FOUND), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		  } catch(BlogAlreadyCreatedException ex) {
			  return new ResponseEntity<Object>(new BlogAlreadyCreatedException("blog already created for this user", ErrorCode.BLOG_ALREADY_EXISTS), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		  } catch (BlogCreationException e) {
			  return new ResponseEntity<Object>(new BlogCreationException("Error creating blog", ErrorCode.BLOG_CREATION_ERROR), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		  
	  }  
}
