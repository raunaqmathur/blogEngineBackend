package com.blog.blogEngine.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blogEngine.dal.BlogRepository;
import com.blog.blogEngine.dal.PostRepository;
import com.blog.blogEngine.dal.UserRepository;
import com.blog.blogEngine.model.Blog;
import com.blog.blogEngine.model.Post;
import com.blog.blogEngine.model.User;
import com.blog.blogEngine.response.exceptions.BlogNotFoundException;
import com.blog.blogEngine.response.exceptions.PostAlreadyPublishedException;
import com.blog.blogEngine.response.exceptions.PostCreationException;
import com.blog.blogEngine.response.exceptions.PostNotFoundException;
import com.blog.blogEngine.response.exceptions.PostUpdationException;
import com.blog.blogEngine.response.exceptions.UserNotFoundException;
import com.blog.blogEngine.response.model.BlogResponse;
import com.blog.blogEngine.response.model.ListPostResponse;
import com.blog.blogEngine.response.model.PostResponse;
import com.blog.blogEngine.response.model.UserResponse;
import com.blog.blogEngine.util.ErrorCode;
import com.blog.blogEngine.util.ModelConvertor;

@Service
public class PostService {
	
	@Autowired
	public BlogRepository blogRepository;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public PostRepository postRepository;
	
	public ListPostResponse getAllPublishedPostByUser(String userName) throws UserNotFoundException {
		User user = userRepository.findByUserName(userName);
		if(user != null) {
				List<Post> listPosts = postRepository.getAllPublishedPostByUserName(user.getId());
				List<PostResponse> lstPostResponse = new ArrayList<PostResponse>();
				for(Post post : listPosts)
					lstPostResponse.add(ModelConvertor.postToPostResponseConvertor(post));
				Blog blog = blogRepository.findByUser(user);
				BlogResponse blogResponse = ModelConvertor.blogToBlogResponseConvertor(blog);
				UserResponse userResponse = ModelConvertor.userToUserResponseConvertor(user);
				return ModelConvertor.postToListPostResponseConvertor(lstPostResponse, blogResponse, userResponse);
		}
		throw new UserNotFoundException("user not found", ErrorCode.USER_NOT_FOUND);
	}
	
	
	public PostResponse insert(String message, String title, String themeId, String userName) throws UserNotFoundException, BlogNotFoundException, PostCreationException {
		User user = userRepository.findByUserName(userName);
		if(user != null && user.getActive() == 1) {
			Blog blog = blogRepository.findByUser(user);
			if(blog == null) {
				throw new BlogNotFoundException("blog not found", ErrorCode.BLOG_NOT_FOUND);
			} else {
				Post post = new Post(user, blog, message, title, new Date(), false, null, themeId, 1);
				Post postSaved = postRepository.insert(post);
				if(postSaved == null) {
					throw new PostCreationException("error creating post", ErrorCode.POST_CREATION_ERROR);
				} else {
					return ModelConvertor.postToPostResponseConvertor(postSaved);
				}
			}
		} else {
			throw new UserNotFoundException("user not found", ErrorCode.USER_NOT_FOUND);
		}
	}
	
	
	public PostResponse update(String message, String title, String themeId, String userName, String id) throws UserNotFoundException, BlogNotFoundException, PostNotFoundException, PostUpdationException, PostAlreadyPublishedException {
		User user = userRepository.findByUserName(userName);
		if(user != null && user.getActive() == 1) {
			Blog blog = blogRepository.findByUser(user);
			if(blog == null) {
				throw new BlogNotFoundException("blog not found", ErrorCode.BLOG_NOT_FOUND);
			} else {
				Optional<Post> postSaved = postRepository.findById(id);
				if(!postSaved.isPresent() || postSaved.get().getActive() == 0) {
					throw new PostNotFoundException("Post not found", ErrorCode.POST_NOT_FOUND);
				} else {
					if(postSaved.get().isPublished())
						throw new PostAlreadyPublishedException("post already published", ErrorCode.POST_ALREADY_PUBLISHED);
					postSaved.get().setMessage(message);
					postSaved.get().setTitle(title);
					postSaved.get().setThemeId(themeId);
					Post postUpdated = postRepository.save(postSaved.get());
					if(postUpdated == null) {
						throw new PostUpdationException("error updating post", ErrorCode.POST_UPDATION_ERROR);
					}
					return ModelConvertor.postToPostResponseConvertor(postUpdated);
				}
			}
		} else {
			throw new UserNotFoundException("user not found", ErrorCode.USER_NOT_FOUND);
		}
	}
	
	public int delete(String postId) throws PostUpdationException, PostNotFoundException {
		Optional<Post> postSaved = postRepository.findById(postId);
		if(postSaved.isPresent() && postSaved.get().getActive() == 1) {
			postSaved.get().setActive(0);
			Post postUpdated = postRepository.save(postSaved.get());
			if(postUpdated == null) {
				throw new PostUpdationException("error updating post", ErrorCode.POST_UPDATION_ERROR);
			}
			return 1;
		} else {
			throw new PostNotFoundException("Post not found", ErrorCode.POST_NOT_FOUND);
		}
	}
	
	public ListPostResponse getPublishedPosts(Date startDate, Date endDate) {
		List<Post> listPosts = postRepository.getAllPublishedPostBetweenDates(startDate, endDate);
		List<PostResponse> lstPostResponse = new ArrayList<PostResponse>();
		for(Post post : listPosts)
			lstPostResponse.add(ModelConvertor.postToPostResponseConvertor(post));
		return ModelConvertor.postToListPostResponseConvertor(lstPostResponse, null, null);
	}
	
	public ListPostResponse getUnPublishedPosts(String userName) throws UserNotFoundException {
		
		User user = userRepository.findByUserName(userName);
		if(user != null) {
				List<Post> listPosts = postRepository.getAllUnpublishedPostByUserName(user.getId());
				List<PostResponse> lstPostResponse = new ArrayList<PostResponse>();
				for(Post post : listPosts)
					lstPostResponse.add(ModelConvertor.postToPostResponseConvertor(post));
				Blog blog = blogRepository.findByUser(user);
				BlogResponse blogResponse = ModelConvertor.blogToBlogResponseConvertor(blog);
				UserResponse userResponse = ModelConvertor.userToUserResponseConvertor(user);
				return ModelConvertor.postToListPostResponseConvertor(lstPostResponse, blogResponse, userResponse);
		}
		
		throw new UserNotFoundException("user not found", ErrorCode.USER_NOT_FOUND);
	}
	
	public int publish(String postId) throws PostUpdationException, PostNotFoundException {
		Optional<Post> postSaved = postRepository.findById(postId);
		if(postSaved.isPresent() && postSaved.get().getActive() == 1) {
			postSaved.get().setPublished(true);
			postSaved.get().setDatePublished(new Date());
			Post postUpdated = postRepository.save(postSaved.get());
			if(postUpdated == null) {
				throw new PostUpdationException("error updating post", ErrorCode.POST_UPDATION_ERROR);
			}
			return 1;
		} else {
			throw new PostNotFoundException("Post not found", ErrorCode.POST_NOT_FOUND);
		}
	}
	
}
