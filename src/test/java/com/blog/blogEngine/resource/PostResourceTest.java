package com.blog.blogEngine.resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.blog.blogEngine.dal.BlogRepository;
import com.blog.blogEngine.dal.PostRepository;
import com.blog.blogEngine.dal.UserRepository;
import com.blog.blogEngine.model.Blog;
import com.blog.blogEngine.model.Post;
import com.blog.blogEngine.model.User;
import com.blog.blogEngine.resource.model.BlogRegistrationData;
import com.blog.blogEngine.resource.model.GetPublishedPostData;
import com.blog.blogEngine.resource.model.PostCreationData;
import com.blog.blogEngine.resource.model.PostUpdationData;
import com.blog.blogEngine.response.model.BlogResponse;
import com.blog.blogEngine.response.model.ListPostResponse;
import com.blog.blogEngine.service.BlogService;
import com.blog.blogEngine.service.PostService;

@RunWith(JUnitPlatform.class)
public class PostResourceTest {
	private static final Logger logger = LogManager.getLogger(PostResourceTest.class);  
	PostResource postResource;
	PostService postService;
	
	BlogRepository blogRepository;
	UserRepository userRepository;
	PostRepository postRepository;
	
	@BeforeEach
	public void setup() {
		postResource = new PostResource();
		postService = new PostService();
		postRepository = Mockito.mock(PostRepository.class);
		blogRepository = Mockito.mock(BlogRepository.class);
		userRepository = Mockito.mock(UserRepository.class);
		postService.postRepository = postRepository;
		postService.userRepository = userRepository;
		postService.blogRepository = blogRepository;
		postResource.postService = postService;
	}
	
	@Test
    public void verifyGetAllPublishedPostByUserPass() {
		logger.info("Starting Test verifygetAllPublishedPostByUserPass");
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserName(anyString())).thenReturn(userTest);
		Blog blog = new Blog(userTest, "website", "name", new Date(),1);
		when(blogRepository.findByUser(any(User.class))).thenReturn(blog);
		
		Post post = new Post(userTest, blog, "message", "title", new Date(), false, null, "themeId", 1);
		List<Post> lstPost = new ArrayList<Post>();
		lstPost.add(post);
		
		when(postRepository.getAllPublishedPostByUserName(anyString())).thenReturn(lstPost);
		
		ResponseEntity<Object> response = postResource.getAllPublishedPostByUser("test");
		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);		
		logger.info("Completed Test verifygetAllPublishedPostByUserPass");
    }
	
	@Test
    public void verifyGetAllPublishedPostByUserFail() {
		logger.info("Starting Test verifyGetAllPublishedPostByUserFail");
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserName(anyString())).thenReturn(null);
		ResponseEntity<Object> response = postResource.getAllPublishedPostByUser("test");
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);		
		logger.info("Completed Test verifyGetAllPublishedPostByUserFail");
    }
	
	
	@Test
    public void verifycreatePostPass() {
		logger.info("Starting Test verifycreatePostPass");
		PostCreationData postCreationData = new PostCreationData("test", "message", "title", "theme");
		
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserName(anyString())).thenReturn(userTest);
		Blog blog = new Blog(userTest, "website", "name", new Date(),1);
		when(blogRepository.findByUser(any(User.class))).thenReturn(blog);
		
		Post post = new Post(userTest, blog, "message", "title", new Date(), false, null, "themeId", 1);
		when(postRepository.insert(any(Post.class))).thenReturn(post);
		
		ResponseEntity<Object> response = postResource.createPost(postCreationData);
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);		
		logger.info("Completed Test verifycreatePostPass");
    }
	
	@Test
    public void verifyUpdatPostPass() {
		logger.info("Starting Test verifyUpdatPostPass");
		PostUpdationData postUpdationData = new PostUpdationData("username", "message1", "title1", "theme1", "id");
		
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserName(anyString())).thenReturn(userTest);
		Blog blog = new Blog(userTest, "website", "name", new Date(),1);
		when(blogRepository.findByUser(any(User.class))).thenReturn(blog);
		
		Post post = new Post(userTest, blog, "message", "title", new Date(), false, null, "themeId", 1);
		Optional<Post> optionalPost = Optional.of(post);
		when(postRepository.findById(anyString())).thenReturn(optionalPost);
		when(postRepository.save(any(Post.class))).thenReturn(post);
		
		ResponseEntity<Object> response = postResource.updatePost(postUpdationData);
		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);		
		logger.info("Completed Test verifyUpdatPostPass");
    }
	
	@Test
    public void verifyGetPublishedPostsWithDatePass() {
		logger.info("Starting Test verifyGetPublishedPostsWithDatePass");
		GetPublishedPostData getPublishedPostData = new GetPublishedPostData();
		getPublishedPostData.setStartDate(new Date());
		getPublishedPostData.setEndDate(new Date());
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserName(anyString())).thenReturn(userTest);
		Blog blog = new Blog(userTest, "website", "name", new Date(),1);
		when(blogRepository.findByUser(any(User.class))).thenReturn(blog);
		
		Post post = new Post(userTest, blog, "message", "title", new Date(), true, new Date(), "themeId", 1);
		List<Post> lstPost = new ArrayList<Post>();
		lstPost.add(post);
		
		when(postRepository.getAllPublishedPostByUserName(anyString())).thenReturn(lstPost);
		
		ResponseEntity<Object> response = postResource.getPublishedPostsWithDate(getPublishedPostData);
		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);			
		logger.info("Completed Test verifyGetPublishedPostsWithDatePass");
    }
	
	
}
