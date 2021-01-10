package com.blog.blogEngine.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.blog.blogEngine.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	public User findByUserNameAndPassword(String userName, String password);
	public User findByUserName(String userName);
}
