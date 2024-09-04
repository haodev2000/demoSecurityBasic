package com.example.demoSecurityJWT.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoSecurityJWT.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
