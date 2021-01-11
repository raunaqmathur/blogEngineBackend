package com.blog.blogEngine.dal;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.blog.blogEngine.model.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String>{
	
	@Query(value = "{ 'post': ?0, 'active' : 1 }",
			sort = "{ 'dateCreated' : -1 }")
	public List<Comment> getAllCommentsForPost(String postId);
}
