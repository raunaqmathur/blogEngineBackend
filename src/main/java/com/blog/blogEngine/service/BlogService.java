package com.blog.blogEngine.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blogEngine.response.exceptions.BlogAlreadyCreatedException;
import com.blog.blogEngine.response.exceptions.BlogCreationException;
import com.blog.blogEngine.response.exceptions.BlogNotFoundException;
import com.blog.blogEngine.response.exceptions.UserNotFoundException;
import com.blog.blogEngine.response.model.BlogResponse;
import com.blog.blogEngine.model.Blog;
import com.blog.blogEngine.model.User;
import com.blog.blogEngine.dal.BlogRepository;
import com.blog.blogEngine.dal.UserRepository;
import com.blog.blogEngine.util.ErrorCode;
import com.blog.blogEngine.util.ModelConvertor;

@Service
public class BlogService {
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public BlogResponse findByUser(String userName) throws UserNotFoundException, BlogNotFoundException {
		User user = userRepository.findByUserName(userName);
		if(user != null) {
			Blog blog = blogRepository.findByUser(user);
			if(blog != null  && blog.getActive() == 1) {
				return ModelConvertor.blogToBlogResponseConvertor(blog);
			}
			throw new BlogNotFoundException("blog not found", ErrorCode.BLOG_NOT_FOUND);
		}
		throw new UserNotFoundException("user not found", ErrorCode.USER_NOT_FOUND);
	}
	
	
	public BlogResponse insert(String website, String name, String userName) throws UserNotFoundException, BlogCreationException, BlogAlreadyCreatedException {
		User user = userRepository.findByUserName(userName);
		if(user != null && user.getActive() == 1) {
			Blog blog = blogRepository.findByUser(user);
			if(blog == null) {
				Blog blogToSave = new Blog(user, website, name, new Date(), 1);
				Blog blogSaved = blogRepository.insert(blogToSave);
				if(blogSaved == null) {
					throw new BlogCreationException("error creating blog", ErrorCode.BLOG_CREATION_ERROR);
				} else {
					return ModelConvertor.blogToBlogResponseConvertor(blogSaved);
				}
			} else {
				throw new BlogAlreadyCreatedException("blog alreay created for this user", ErrorCode.BLOG_ALREADY_EXISTS);
			}
		} else {
			throw new UserNotFoundException("user not found", ErrorCode.USER_NOT_FOUND);
		}
		
	}
	
}
