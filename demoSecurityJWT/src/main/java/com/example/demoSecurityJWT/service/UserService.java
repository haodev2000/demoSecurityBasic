package com.example.demoSecurityJWT.service;

import java.util.List;

import com.example.demoSecurityJWT.entity.Users;

public interface UserService  {
	Users addUser(Users user);
	Users findByUsername(String username);
	Users findByUserNameAndPassword(String username, String password);
	List<Users> getList();
	boolean checkLogin(String username, String password);
}
