package com.blog.blogEngine.resource;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.blog.blogEngine.dal.UserRepository;
import com.blog.blogEngine.model.User;
import com.blog.blogEngine.resource.model.RegistrationData;
import com.blog.blogEngine.resource.model.SignInData;
import com.blog.blogEngine.response.exceptions.BlogAlreadyCreatedException;
import com.blog.blogEngine.response.exceptions.BlogCreationException;
import com.blog.blogEngine.response.exceptions.UserNotFoundException;
import com.blog.blogEngine.response.model.BlogResponse;
import com.blog.blogEngine.response.model.UserResponse;
import com.blog.blogEngine.service.BlogService;
import com.blog.blogEngine.service.UserService;
import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;  
import org.junit.platform.runner.JUnitPlatform;

@RunWith(JUnitPlatform.class)
public class UserResourceTest {
	private static final Logger logger = LogManager.getLogger(UserResourceTest.class);  
	UserResource userResource;
	UserService userService;
	BlogService blogService;
	
	UserRepository userRepository;
	
	@BeforeEach
	public void setup() {
		userResource = new UserResource();
		userService = new UserService();
		blogService = Mockito.mock(BlogService.class);
		userRepository = Mockito.mock(UserRepository.class);
		userService.userRepository = userRepository;
		userService.blogService = blogService;
		userResource.userService = userService;
	}
	
	@Test
    public void verifySignInPass() {
		logger.info("Starting Test verifySignInPass");
		SignInData signInData = new SignInData();
		signInData.setUserName("test");
		signInData.setPassword("password");
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserNameAndPassword(anyString(), anyString())).thenReturn(userTest);
		ResponseEntity<Object> response = userResource.signIn(signInData);
		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
		UserResponse userResponse = (UserResponse)response.getBody();
		assertEquals(userResponse.getFirstName(), userTest.getFirstName());
		assertEquals(userResponse.getLastName(), userTest.getLastName());
		assertEquals(userResponse.getEmail(), userTest.getEmail());
		assertEquals(userResponse.getUserName(), userTest.getUserName());
		logger.info("Completed Test verifySignInPass");
    }
	
	@Test
    public void verifySignInFail() {
		logger.info("Starting Test verifySignInFail");
		SignInData signInData = new SignInData();
		signInData.setUserName("test");
		signInData.setUserName("password");
		when(userRepository.findByUserNameAndPassword(anyString(), anyString())).thenReturn(null);
		ResponseEntity<Object> response = userResource.signIn(signInData);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		logger.info("Completed Test verifySignInFail");
    }
	
	@Test
    public void verifyRegisterPass() throws UserNotFoundException, BlogCreationException, BlogAlreadyCreatedException {
		logger.info("Starting Test verifyRegisterPass");
		RegistrationData registrationData = new RegistrationData();
		registrationData.setUserName("test");
		registrationData.setPassword("password");
		registrationData.setFirstName("firstName");
		registrationData.setLastName("lastName");
		registrationData.setEmail("email");
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserName(anyString())).thenReturn(null);
		when(userRepository.insert(any(User.class))).thenReturn(userTest);
		when(blogService.insert(anyString(), anyString(), anyString())).thenReturn(Mockito.mock(BlogResponse.class));
		ResponseEntity<Object> response = userResource.register(registrationData);
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		UserResponse userResponse = (UserResponse)response.getBody();
		assertEquals(userResponse.getFirstName(), registrationData.getFirstName());
		assertEquals(userResponse.getLastName(), registrationData.getLastName());
		assertEquals(userResponse.getEmail(), registrationData.getEmail());
		assertEquals(userResponse.getUserName(), registrationData.getUserName());
		logger.info("Completed Test verifyRegisterPass");
    }
	
	@Test
    public void verifyRegisterFail() {
		logger.info("Starting Test verifyRegisterFail");
		RegistrationData registrationData = new RegistrationData();
		registrationData.setUserName("test");
		registrationData.setPassword("password");
		registrationData.setFirstName("firstName");
		registrationData.setLastName("lastName");
		registrationData.setEmail("email");
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(),1);
		when(userRepository.findByUserName(anyString())).thenReturn(userTest);
		ResponseEntity<Object> response = userResource.register(registrationData);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		logger.info("Completed Test verifyRegisterFail");
    }
	
	@Test
    public void verifyDeleteAccountPass() {
		logger.info("Starting Test verifyDeleteAccountPass");
		User userTest = new User("firstName", "lastName", "test", "password", "email", new Date(), 1);
		when(userRepository.findByUserName(anyString())).thenReturn(userTest);
		ResponseEntity<Object> response = userResource.deleteAccount("test");
		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
		logger.info("Completed Test verifyDeleteAccountPass");
    }
	
	@Test
    public void verifyDeleteAccountFail() {
		logger.info("Starting Test verifyDeleteAccountPass");
		when(userRepository.findByUserName(anyString())).thenReturn(null);
		ResponseEntity<Object> response = userResource.deleteAccount("test");
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		logger.info("Completed Test verifyDeleteAccountPass");
    }
	
}
