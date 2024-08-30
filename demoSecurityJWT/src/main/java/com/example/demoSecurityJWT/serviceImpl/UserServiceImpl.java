package com.example.demoSecurityJWT.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demoSecurityJWT.entity.Users;
import com.example.demoSecurityJWT.reponsitory.UserReponsitory;
import com.example.demoSecurityJWT.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserReponsitory userReponsitory;
	
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public Users addUser(Users userDTO) {

		return userReponsitory.save(userDTO);
	}

	@Override
	 public Users findByUsername(String username) {
	        return userReponsitory.findByUsername(username);
	    }

	@Override
	public List<Users> getList() {
		// TODO Auto-generated method stub
		return userReponsitory.findAll();
	}

	@Override
	public Users findByUserNameAndPassword(String username, String password) {
		 Users user = userReponsitory.findByUsername(username);
	        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
	            return user;
	        }else {
	        	 return null;
	        }
	       
	}

	@Override
	public boolean checkLogin(String username, String password) {
		Users user = userReponsitory.findByUsername(username);
		
		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }else {
        	 return false;
        }
			
	}
	
	
	
}
