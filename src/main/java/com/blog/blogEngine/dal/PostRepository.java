package com.blog.blogEngine.dal;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.blog.blogEngine.model.Blog;
import com.blog.blogEngine.model.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{
	
	@Query(value = "{ 'user': ?0, 'published' : true, 'active' : 1 }",
			sort = "{ 'datePublished' : -1 }")
	public List<Post> getAllPublishedPostByUserName(String userId);
	public List<Post> findByBlog(Blog blog);
	public List<Post> findByPublished(boolean published);
	
	@Query(value = "{ 'datePublished' : { $gt: ?0, $lt: ?1 } , 'published' : true, 'active' : 1 }",
			sort = "{ 'datePublished' : -1 }")
	public List<Post> getAllPublishedPostBetweenDates(Date startDate, Date endDate);
	
	@Query(value = "{ 'published' : true, 'active' : 1 }",
			sort = "{ 'datePublished' : -1 }")
	public List<Post> getAllPublishedPost();
	
	@Query(value = "{ 'user': ?0, 'published' : false, 'active' : 1 }",
			sort = "{ 'dateCreated' : -1 }")
	public List<Post> getAllUnpublishedPostByUserName(String userId);
}
