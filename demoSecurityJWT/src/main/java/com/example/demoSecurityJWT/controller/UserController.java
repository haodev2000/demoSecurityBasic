package com.example.demoSecurityJWT.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoSecurityJWT.entity.Users;
import com.example.demoSecurityJWT.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;

	@GetMapping("/getAll")
	public List<Users> getAll() {
		return userServiceImpl.getList();
	}
	
}
