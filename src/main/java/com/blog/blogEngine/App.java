package com.blog.blogEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import com.blog.blogEngine.dal.UserRepository;

@SpringBootApplication
@EnableMongoRepositories({"com.blog.blogEngine.dal"})
public class App 
{
	@Autowired
	UserRepository userRepository;
	
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
