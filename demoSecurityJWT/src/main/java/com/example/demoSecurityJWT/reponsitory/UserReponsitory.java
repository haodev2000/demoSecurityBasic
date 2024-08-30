package com.example.demoSecurityJWT.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoSecurityJWT.entity.Users;

@Repository
public interface UserReponsitory extends JpaRepository<Users, Integer> {

	Users findByUsername(String username);
	Users findByUsernameAndPassword(String username, String password);
	

	
}
