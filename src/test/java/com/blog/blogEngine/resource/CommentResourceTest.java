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
import com.blog.blogEngine.dal.CommentRepository;
import com.blog.blogEngine.dal.PostRepository;
import com.blog.blogEngine.dal.UserRepository;
import com.blog.blogEngine.model.Blog;
import com.blog.blogEngine.model.Comment;
import com.blog.blogEngine.model.Post;
import com.blog.blogEngine.model.User;
import com.blog.blogEngine.resource.model.BlogRegistrationData;
import com.blog.blogEngine.resource.model.CommentCreationData;
import com.blog.blogEngine.resource.model.GetPublishedPostData;
import com.blog.blogEngine.resource.model.PostCreationData;
import com.blog.blogEngine.resource.model.PostUpdationData;
import com.blog.blogEngine.response.model.BlogResponse;
import com.blog.blogEngine.response.model.ListPostResponse;
import com.blog.blogEngine.service.BlogService;
import com.blog.blogEngine.service.CommentService;
import com.blog.blogEngine.service.PostService;

@RunWith(JUnitPlatform.class)
public class CommentResourceTest {
	private static final Logger logger = LogManager.getLogger(CommentResourceTest.class);  
	CommentResource commentResource;
	CommentService commentService;
	
	UserRepository userRepository;
	PostRepository postRepository;
	CommentRepository commentRepository;
	@BeforeEach
	public void setup() {
		commentResource = new CommentResource();
		commentService = new CommentService();
		postRepository = Mockito.mock(PostRepository.class);
		commentRepository = Mockito.mock(CommentRepository.class);
		userRepository = Mockito.mock(UserRepository.class);
		commentService.postRepository = postRepository;
		commentService.userRepository = userRepository;
		commentService.commentRepository = commentRepository;
		commentResource.commentService = commentService;
	}
	
	@Test
    public void verifyGetAllCommentsForPostPass() {
		logger.info("Starting Test verifyGetAllCommentsForPostPass");
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserName(anyString())).thenReturn(userTest);
		Blog blog = new Blog(userTest, "website", "name", new Date(),1);
		Post post = new Post(userTest, blog, "message", "title", new Date(), false, null, "themeId", 1);
		Optional<Post> postRetrieved = Optional.of(post);
		when(postRepository.findById(anyString())).thenReturn(postRetrieved);
		Comment comment = new Comment(userTest, post, "message", new Date(), 1);
		List<Comment> lstComments = new ArrayList<>();
		lstComments.add(comment);
		when(commentRepository.getAllCommentsForPost(anyString())).thenReturn(lstComments);
		ResponseEntity<Object> response = commentResource.getAllCommentsForPost("postId");
		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);		
		logger.info("Completed Test verifyGetAllCommentsForPostPass");
    }
	
	@Test
    public void verifyCreatePostPass() {
		logger.info("Starting Test verifyCreatePostPass");
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserName(anyString())).thenReturn(userTest);
		Blog blog = new Blog(userTest, "website", "name", new Date(),1);
		Post post = new Post(userTest, blog, "message", "title", new Date(), false, null, "themeId", 1);
		Optional<Post> postRetrieved = Optional.of(post);
		when(postRepository.findById(anyString())).thenReturn(postRetrieved);
		Comment comment = new Comment(userTest, post, "message", new Date(), 1);
		CommentCreationData commentCreationData = new CommentCreationData();
		commentCreationData.setMessage("message");
		commentCreationData.setPostId("postId");
		commentCreationData.setUserName("userName");
		when(commentRepository.insert(any(Comment.class))).thenReturn(comment);
		ResponseEntity<Object> response = commentResource.createPost(commentCreationData);
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);		
		logger.info("Completed Test verifyCreatePostPass");
    }
}
