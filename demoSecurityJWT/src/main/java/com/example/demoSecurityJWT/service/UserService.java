package com.example.demoSecurityJWT.service;

import java.util.List;

import com.example.demoSecurityJWT.dto.UsersDTO;
import com.example.demoSecurityJWT.entity.Users;

public interface UserService  {
	UsersDTO addUser(UsersDTO usersDTO);
	Users findByUsername(String username);
	Users findByEmail(String email);
	Users findByUserNameAndPassword(String username, String password);
	List<Users> getList();
	boolean checkLogin(String username, String password);
	

}
