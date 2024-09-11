package com.example.demoSecurityJWT.controller;

public class InvalidOldPasswordException extends Exception {
	 public InvalidOldPasswordException(String message) {
	        super(message);
	    }
}
