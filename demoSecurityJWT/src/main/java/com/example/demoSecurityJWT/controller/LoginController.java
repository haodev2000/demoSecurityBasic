package com.example.demoSecurityJWT.controller;

import java.security.Principal;
import java.util.Locale;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoSecurityJWT.dto.UsersDTO;
import com.example.demoSecurityJWT.entity.Users;
import com.example.demoSecurityJWT.payload.GenericResponse;
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
	
//    @PostMapping("/user/changePassword")
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//    public ResponseEntity<?> changeUserPassword(@RequestParam("oldPassword") String oldPassword,
//                                                @RequestParam("newPassword") String newPassword) {
//        // Get the currently authenticated user
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Users user = userServiceImpl.findByUsername(username);
//        System.out.println("-------------------" + username);
//        
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found for username: " + username);
//        }
//
//        if (!userServiceImpl.checkIfValidOldPassword(user, oldPassword)) {
//            return new ResponseEntity<>(new GenericResponse("Invalid old password", false), HttpStatus.BAD_REQUEST);
//        }
//
//        // Change the user's password
//        userServiceImpl.changeUserPassword(user, newPassword);
//
//        return new ResponseEntity<>(new GenericResponse("Password changed successfully", true), HttpStatus.OK);
//    }

    @PostMapping("/user/changePassword")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> changeUserPassword(@RequestParam("oldPassword") String oldPassword,
                                                @RequestParam("newPassword") String newPassword,
                                                @RequestParam("confirmNewPassword") String confirmNewPassword,
                                                Principal principal) {
    	 try {
    	        // Ensure new password and confirm password match
    	        if (!newPassword.equals(confirmNewPassword)) {
    	            return new ResponseEntity<>("New passwords do not match", HttpStatus.BAD_REQUEST);
    	        }

    	        // Lấy tên người dùng từ SecurityContext
//    	        String username = SecurityContextHolder.getContext().getAuthentication().getName();

    	        if (principal.getName() == null || principal.getName().isEmpty()) {
    	            return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
    	        }

    	        // Call the service method to update the password for the currently authenticated user
    	        userServiceImpl.updatePassword(principal.getName(), oldPassword, newPassword);

    	        return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);

    	    } catch (Exception e) {
    	        // Handle exceptions during password update process
    	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    	    }
    }

   
	
}
