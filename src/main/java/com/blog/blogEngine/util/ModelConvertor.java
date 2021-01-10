package com.blog.blogEngine.util;

import com.blog.blogEngine.model.User;
import com.blog.blogEngine.response.model.UserResponse;

public class ModelConvertor {

	public static UserResponse userToUserResponseConvertor(User user) {
		return new UserResponse(user.getFirstName(),
				user.getLastName(),
				user.getUserName(),
				user.getEmail(),
				user.getDateCreated(),
				user.getActive());
	}
	
}
