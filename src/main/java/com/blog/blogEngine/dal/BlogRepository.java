package com.blog.blogEngine.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.blog.blogEngine.model.Blog;
import com.blog.blogEngine.model.User;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String>{
	public Blog findByUser(User user);
}
