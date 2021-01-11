package com.blog.blogEngine.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blogEngine.dal.CommentRepository;
import com.blog.blogEngine.dal.PostRepository;
import com.blog.blogEngine.dal.UserRepository;
import com.blog.blogEngine.model.Comment;
import com.blog.blogEngine.model.Post;
import com.blog.blogEngine.model.User;
import com.blog.blogEngine.response.exceptions.CommentCreationException;
import com.blog.blogEngine.response.exceptions.PostNotFoundException;
import com.blog.blogEngine.response.exceptions.UserNotFoundException;
import com.blog.blogEngine.response.model.CommentResponse;
import com.blog.blogEngine.util.ErrorCode;
import com.blog.blogEngine.util.ModelConvertor;

@Service
public class CommentService {
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public PostRepository postRepository;
	
	@Autowired
	public CommentRepository commentRepository;

	
	public List<CommentResponse> getAllCommentsForPost(String postId) throws PostNotFoundException  {
		Optional<Post> post = postRepository.findById(postId);
		if(post.isPresent()) {
				List<Comment> listComments = commentRepository.getAllCommentsForPost(postId);
				List<CommentResponse> lstCommentResponse = new ArrayList<CommentResponse>();
				for(Comment comment : listComments)
					lstCommentResponse.add(ModelConvertor.commentToCommentResponseConvertor(comment));
				return lstCommentResponse;
		}
		throw new PostNotFoundException("post not found", ErrorCode.POST_NOT_FOUND);
	}
	
	
	public CommentResponse insert(String message, String userName, String postId) throws UserNotFoundException, PostNotFoundException, CommentCreationException {
		User user = userRepository.findByUserName(userName);
		if(user != null && user.getActive() == 1) {
			Optional<Post> post = postRepository.findById(postId);
			if(!post.isPresent() || post.get().getActive() == 0) {
				throw new PostNotFoundException("post not found", ErrorCode.POST_NOT_FOUND);
			} else {
				Comment comment = new Comment(user, post.get(), message, new Date(), 1);
				Comment commentSaved = commentRepository.insert(comment);
				if(commentSaved == null) {
					throw new CommentCreationException("error creating comment", ErrorCode.COMMENT_CREATION_ERROR);
				} else {
					return ModelConvertor.commentToCommentResponseConvertor(commentSaved);
				}
			}
		} else {
			throw new UserNotFoundException("user not found", ErrorCode.USER_NOT_FOUND);
		}
	}
}
