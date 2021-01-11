package com.blog.blogEngine.resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.blog.blogEngine.dal.BlogRepository;
import com.blog.blogEngine.dal.UserRepository;
import com.blog.blogEngine.model.Blog;
import com.blog.blogEngine.model.User;
import com.blog.blogEngine.resource.model.BlogRegistrationData;
import com.blog.blogEngine.response.model.BlogResponse;
import com.blog.blogEngine.service.BlogService;

@RunWith(JUnitPlatform.class)
public class BlogResourceTest {
	private static final Logger logger = LogManager.getLogger(BlogResourceTest.class);  
	BlogResource blogResource;
	BlogService blogService;
	
	
	BlogRepository blogRepository;
	UserRepository userRepository;
	
	@BeforeEach
	public void setup() {
		blogResource = new BlogResource();
		blogService = new BlogService();
		blogRepository = Mockito.mock(BlogRepository.class);
		userRepository = Mockito.mock(UserRepository.class);
		blogService.blogRepository = blogRepository;
		blogService.userRepository = userRepository;
		blogResource.blogService = blogService;
	}
	
	@Test
    public void verifyGetBlogByUserPass() {
		logger.info("Starting Test verifyGetBlogByUserPass");
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserName(anyString())).thenReturn(userTest);
		Blog blog = new Blog(userTest, "website", "name", new Date(),1);
		when(blogRepository.findByUser(any(User.class))).thenReturn(blog);
		ResponseEntity<Object> response = blogResource.getBlogByUser("test");
		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
		BlogResponse blogResponse = (BlogResponse)response.getBody();
		assertEquals(blogResponse.getName(), blog.getName());
		assertEquals(blogResponse.getUserResponse().getEmail(), userTest.getEmail());
		logger.info("Completed Test verifyGetBlogByUserPass");
    }
	
	@Test
    public void verifyGetBlogByUserFail() {
		logger.info("Starting Test verifyGetBlogByUserFail");
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserName(anyString())).thenReturn(userTest);
		when(blogRepository.findByUser(any(User.class))).thenReturn(null);
		ResponseEntity<Object> response = blogResource.getBlogByUser("test");
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		logger.info("Completed Test verifyGetBlogByUserFail");
    }
	
	@Test
    public void verifyRegisterPass() {
		logger.info("Starting Test verifyRegisterPass");
		BlogRegistrationData blogRegistrationData = new BlogRegistrationData("test", "website", "name");
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserName(anyString())).thenReturn(userTest);
		Blog blog = new Blog(userTest, "website", "name", new Date(),1);
		when(blogRepository.insert(any(Blog.class))).thenReturn(blog);
		ResponseEntity<Object> response = blogResource.register(blogRegistrationData);
		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
		BlogResponse blogResponse = (BlogResponse)response.getBody();
		assertEquals(blogResponse.getName(), blog.getName());
		assertEquals(blogResponse.getUserResponse().getEmail(), userTest.getEmail());
		logger.info("Completed Test verifyRegisterPass");
    }
	
	
	@Test
    public void verifyRegisterFail() {
		logger.info("Starting Test verifyRegisterFail");
		BlogRegistrationData blogRegistrationData = new BlogRegistrationData("test", "website", "name");
		when(userRepository.findByUserName(anyString())).thenReturn(null);

		ResponseEntity<Object> response = blogResource.register(blogRegistrationData);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		logger.info("Completed Test verifyRegisterFail");
    }
}
