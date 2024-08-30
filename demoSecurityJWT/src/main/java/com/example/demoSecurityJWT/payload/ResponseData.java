package com.example.demoSecurityJWT.payload;

import lombok.Data;

@Data
public class ResponseData {

	private int status = 200;
	private String desc;
	private Object data;
	private boolean isSuccess = true;
	
}
