package com.blog.blogEngine.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blogEngine.response.exceptions.BlogAlreadyCreatedException;
import com.blog.blogEngine.response.exceptions.BlogCreationException;
import com.blog.blogEngine.response.exceptions.UserNotFoundException;
import com.blog.blogEngine.response.model.UserResponse;
import com.blog.blogEngine.model.User;
import com.blog.blogEngine.dal.UserRepository;
import com.blog.blogEngine.util.ModelConvertor;

@Service
public class UserService {
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public BlogService blogService;
	
	/**
	   * This method is finds the user object based on userName and password provided.
	   * @param userName This is the first parameter to findByUserNameAndPassword method
	   * @param password This is the second parameter to findByUserNameAndPassword method
	   * @return ResponseEntity<Object> This returns UserResponse object if found or throw respective exception.
	*/
	public UserResponse findByUserNameAndPassword(String userName, String password) {
		User user = userRepository.findByUserNameAndPassword(userName, password);
		if(user != null  && user.getActive() == 1) {
			return ModelConvertor.userToUserResponseConvertor(user);
		}
		return null;
	}
	
	/**
	   * This method is finds the user object based on userName.
	   * @param userName This is the first parameter to findByUserName method
	   * @return ResponseEntity<Object> This returns UserResponse object if found or throw respective exception.
	*/
	public UserResponse findByUserName(String userName) {
		User user = userRepository.findByUserName(userName);
		if(user != null && user.getActive() == 1) {
			return ModelConvertor.userToUserResponseConvertor(user);
		}
		return null;
	}
	
	/**
	   * This method is used to insert a new user and create a new blog.
	   * @param firstName This is the first parameter to insert method
	   * @param lastName This is the second parameter to insert method
	   * @param userName This is the third parameter to insert method
	   * @param password This is the fourth parameter to insert method
	   * @param email This is the fifth parameter to insert method
	   * @param blogName This is the fifth parameter to insert method
	   * @param website This is the fifth parameter to insert method
	   * @return ResponseEntity<Object> This returns UserResponse object or throw respective exception.
	*/
	public UserResponse insert(String firstName, String lastName, String userName, String password, String email,
			String blogName, String website) 
			throws UserNotFoundException, BlogCreationException, BlogAlreadyCreatedException {
		User newUser = new User(firstName, lastName, userName, password, email, new Date(), 1);
		User user = userRepository.insert(newUser);
		if(user != null) {
			try {
				blogService.insert(website, blogName, userName);
			} catch (UserNotFoundException | BlogCreationException | BlogAlreadyCreatedException e) {
				throw e;
			} 
			return ModelConvertor.userToUserResponseConvertor(user);
		}
		return null;
	}
	
	/**
	   * This method is used to remove a user.
	   * @param userName This is the first parameter to deleteAccount method
	   * @return ResponseEntity<Object> This returns int.
	*/
	public int deleteAccount(String userName) {
		User user = userRepository.findByUserName(userName);
		if(user != null && user.getActive() == 1) {
			user.setActive(0);
			userRepository.save(user);
			return 1;
		}
		return 0;
	}
}
