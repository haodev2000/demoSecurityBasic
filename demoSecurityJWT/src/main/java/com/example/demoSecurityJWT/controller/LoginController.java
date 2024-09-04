package com.example.demoSecurityJWT.controller;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoSecurityJWT.dto.UsersDTO;
import com.example.demoSecurityJWT.entity.Users;
import com.example.demoSecurityJWT.payload.ResponseData;
import com.example.demoSecurityJWT.serviceImpl.UserServiceImpl;
import com.example.demoSecurityJWT.utils.JwtUtilsHelper;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/api")
public class LoginController {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	JwtUtilsHelper jwtUtilsHelper;
	

	 @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
		 	Users user = userServiceImpl.findByUserNameAndPassword(username, password);
		 	
		 	ResponseData responseData = new ResponseData();
		 	
		 	boolean isLogin = userServiceImpl.checkLogin(username, password);
		 	
//		 	SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//		 	String encrypted = Encoders.BASE64.encode(secretKey.getEncoded());
//		 	
//		 	System.out.println(encrypted);
		 	 if (isLogin) {
		           
		            String token =  jwtUtilsHelper.generateToken(username);
		            responseData.setData(token);
		 		 
//		 		responseData.setData("true");
		        } else {
		        	responseData.setData("");
		            responseData.setSuccess(false);
		        }
		 	 return new ResponseEntity<>(responseData, HttpStatus.OK);
	    }
	 
	 @GetMapping("/authen")
	 public String login1(Authentication authentication) {
	     String username = authentication.getName();
	     return username;
	 }


	@PostMapping("/signUp")
	public UsersDTO signup(@RequestBody UsersDTO u) {
		return userServiceImpl.addUser(u);
	}
	
	
	@GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Welcome to the Admin Dashboard!");
    }

    @GetMapping("/user/profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> userProfile() {
        return ResponseEntity.ok("Welcome to your Profile!");
    }
	
	
	
}
