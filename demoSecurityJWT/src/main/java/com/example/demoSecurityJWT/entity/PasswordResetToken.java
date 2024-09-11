package com.example.demoSecurityJWT.entity;

import java.util.Date;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class PasswordResetToken {

	 	private static final int EXPIRATION = 60 * 24;
	 
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	 
	    private String token;
	 
	    @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
	    @JoinColumn(nullable = false, name = "user_id")
	    private Users user;
	 
	    private Date expiryDate;
	
}
