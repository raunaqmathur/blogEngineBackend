package com.blog.blogEngine.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blog.blogEngine.response.model.UserResponse;
import com.blog.blogEngine.model.User;
import com.blog.blogEngine.dal.UserRepository;
import com.blog.blogEngine.util.ModelConvertor;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserResponse findByUserNameAndPassword(String userName, String password) {
		User user = userRepository.findByUserNameAndPassword(userName, password);
		if(user != null  && user.getActive() == 1) {
			return ModelConvertor.userToUserResponseConvertor(user);
		}
		return null;
	}
	
	public UserResponse findByUserName(String userName) {
		User user = userRepository.findByUserName(userName);
		if(user != null && user.getActive() == 1) {
			return ModelConvertor.userToUserResponseConvertor(user);
		}
		return null;
	}
	
	
	public UserResponse insert(String firstName, String lastName, String userName, String password, String email) {
		User newUser = new User(firstName, lastName, userName, password, email, new Date(), 1);
		User user = userRepository.insert(newUser);
		if(user != null) {
			return ModelConvertor.userToUserResponseConvertor(user);
		}
		return null;
	}
	
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
