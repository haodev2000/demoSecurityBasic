package com.example.demoSecurityJWT.reponsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoSecurityJWT.entity.Users;

@Repository
public interface UserReponsitory extends JpaRepository<Users, Integer> {

    Optional<Users> findByUsername(String username);
    
    // You can keep this method, but using the custom login handler is preferred
    Users findByUsernameAndPassword(String username, String password);
}

