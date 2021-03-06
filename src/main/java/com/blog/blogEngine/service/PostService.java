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
	
	/**
	   * This method is to fetch all published posts by an user.
	   * @param userName This is the first parameter to getAllPublishedPostByUser method
	   * @return ListPostResponse This returns list of posts.
	*/
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
	
	/**
	   * This method is to insert a new post.
	   * @param message This is the first parameter to insert method
	   * @param title This is the second parameter to insert method
	   * @param themeId This is the third parameter to insert method
	   * @param userName This is the fourth parameter to insert method
	   * @return PostResponse This returns the post object.
	*/
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
	
	/**
	   * This method is to update a post.
	   * @param message This is the first parameter to update method
	   * @param title This is the second parameter to update method
	   * @param themeId This is the third parameter to update method
	   * @param userName This is the fourth parameter to update method
	   * @param id This is the fifth parameter to update method
	   * @return PostResponse This returns the updated post object.
	*/
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
	
	/**
	   * This method is to delete a post.
	   * @param id This is the first parameter to delete method
	   * @return int This returns 0/1 or throw exception accordingly.
	*/
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
	
	/**
	   * This method is to get a post.
	   * @param id This is the first parameter to delete method
	   * @return int This returns 0/1 or throw exception accordingly.
	*/
	public PostResponse getPost(String postId) throws PostUpdationException, PostNotFoundException {
		Optional<Post> postSaved = postRepository.findById(postId);
		if(postSaved.isPresent() && postSaved.get().getActive() == 1) {
			return ModelConvertor.postToPostResponseConvertor(postSaved.get());
		} else {
			throw new PostNotFoundException("Post not found", ErrorCode.POST_NOT_FOUND);
		}
	}
	
	/**
	   * This method is to fetch all published posts between start and end date.
	   * @param startDate This is the first parameter to getPublishedPostsWithDate method
	   * @param endDate This is the first parameter to getPublishedPosts method
	   * @return ListPostResponse This returns list of posts.
	*/
	public ListPostResponse getPublishedPostsWithDate(Date startDate, Date endDate) {
		List<Post> listPosts = postRepository.getAllPublishedPostBetweenDates(startDate, endDate);
		List<PostResponse> lstPostResponse = new ArrayList<PostResponse>();
		for(Post post : listPosts)
			lstPostResponse.add(ModelConvertor.postToPostResponseConvertor(post));
		return ModelConvertor.postToListPostResponseConvertor(lstPostResponse, null, null);
	}
	
	/**
	   * This method is to fetch all published posts.
	   * @return ListPostResponse This returns list of posts.
	*/
	public ListPostResponse getPublishedPosts() {
		List<Post> listPosts = postRepository.getAllPublishedPost();
		List<PostResponse> lstPostResponse = new ArrayList<PostResponse>();
		for(Post post : listPosts)
			lstPostResponse.add(ModelConvertor.postToPostResponseConvertor(post));
		return ModelConvertor.postToListPostResponseConvertor(lstPostResponse, null, null);
	}
	
	/**
	   * This method is to fetch all published posts for an user.
	   * @param userName This is the first parameter to getUnPublishedPosts method
	   * @return ListPostResponse This returns list of posts.
	*/
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
