package com.blog.blogEngine.resource;

import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.blog.blogEngine.resource.model.RegistrationData;
import com.blog.blogEngine.resource.model.SignInData;
import com.blog.blogEngine.response.exceptions.*;
import com.blog.blogEngine.response.model.UserResponse;
import com.blog.blogEngine.service.UserService;
import com.blog.blogEngine.util.ErrorCode;

@Controller
public class UserResource {
	  private static final Logger logger = LogManager.getLogger(UserResource.class);  
	
	  @Autowired
	  UserService userService;
	
	  @PostMapping("/signin")
	  @ResponseBody
	  public ResponseEntity<Object> signIn(@RequestBody SignInData signInData) {
		  UserResponse userReturned = userService.findByUserNameAndPassword(signInData.getUserName(), signInData.getPassword());
		  if(userReturned == null) {
			  return new ResponseEntity<Object>(new UserNotFoundException("user not found", ErrorCode.USER_NOT_FOUND), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		  }
	      return new ResponseEntity<Object>(userReturned, new HttpHeaders(), HttpStatus.ACCEPTED);
	  }
	  
	  @PostMapping("/register")
	  @ResponseBody
	  public ResponseEntity<Object> register(@RequestBody RegistrationData registrationData) {
		  UserResponse userReturned = userService.findByUserName(registrationData.userName);
		  if(userReturned != null) {
			  return new ResponseEntity<Object>(new UserAlreadyExistsException("username already exists", ErrorCode.USER_ALREADY_EXISTS), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		  } else {
			  UserResponse userSaved = userService.insert(registrationData.getFirstName(), registrationData.getLastName(), 
					  registrationData.getUserName(), registrationData.getPassword(), registrationData.getEmail());
			  if(userSaved != null) {
				  return new ResponseEntity<Object>(userSaved, new HttpHeaders(), HttpStatus.CREATED);
			  } else {
				  return new ResponseEntity<Object>(new UserCreationException("Error creating user", ErrorCode.USER_CREATION_ERROR), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			  }
		  }
	  }
	  
	  @PostMapping("/deleteAccount")
	  @ResponseBody
	  public ResponseEntity<Object> deleteAccount(@RequestParam(name = "userName", required = true) String userName) {
		  int returnCode = userService.deleteAccount(userName);
		  if(returnCode == 0) {
			  return new ResponseEntity<Object>(new UserNotFoundException("user not found", ErrorCode.USER_NOT_FOUND), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		  }
	      return new ResponseEntity<Object>("User account deactivated", new HttpHeaders(), HttpStatus.ACCEPTED);
	  }
}
