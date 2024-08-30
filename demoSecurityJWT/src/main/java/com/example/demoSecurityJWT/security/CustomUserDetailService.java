package com.example.demoSecurityJWT.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demoSecurityJWT.entity.Users;
import com.example.demoSecurityJWT.reponsitory.UserReponsitory;


@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	UserReponsitory userReponsitory;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users u = userReponsitory.findByUsername(username);
		
		if(u == null) {
			throw new UsernameNotFoundException("User not found !");
		}
		
		return new User(username,u.getPassword(), new ArrayList<>());
	}

}
