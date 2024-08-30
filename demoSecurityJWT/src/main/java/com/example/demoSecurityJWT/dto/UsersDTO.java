package com.example.demoSecurityJWT.dto;

import lombok.Data;

@Data

public class UsersDTO {

    private Integer id;

    private String username;
    private String password;


    private RoleDTO role;
	 	
}
