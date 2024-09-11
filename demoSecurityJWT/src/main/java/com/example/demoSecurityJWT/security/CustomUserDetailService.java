package com.example.demoSecurityJWT.security;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demoSecurityJWT.entity.Users;
import com.example.demoSecurityJWT.reponsitory.UserReponsitory;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserReponsitory userReponsitory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user by username from the repository, handle Optional properly
//        Optional<Users> userOptional = userReponsitory.findByUsername(username);
//
//        // If the user is not found, throw an exception
//        Users u = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found for username: " + username));
//
//        // Return a Spring Security User object with username, password, and empty authorities (for now)
//        return new User(u.getUsername(), u.getPassword(), new ArrayList<>());
    	
    	  Users user = userReponsitory.findByUsername(username)
                  .orElseThrow(() -> new UsernameNotFoundException("User not found for username: " + username));

          // Return a Spring Security User object with username, password, and empty authorities (for now)
          return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}