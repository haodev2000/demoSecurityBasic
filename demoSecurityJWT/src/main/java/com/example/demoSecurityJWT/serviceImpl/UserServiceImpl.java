package com.example.demoSecurityJWT.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demoSecurityJWT.dto.UsersDTO;
import com.example.demoSecurityJWT.entity.Role;
import com.example.demoSecurityJWT.entity.Users;
import com.example.demoSecurityJWT.reponsitory.RoleRepository;
import com.example.demoSecurityJWT.reponsitory.UserReponsitory;
import com.example.demoSecurityJWT.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserReponsitory userRepository;
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users findByUsername(String username) {
        // Return the user if present, otherwise throw an exception
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for username: " + username));
    }

    @Override
    public List<Users> getList() {
        // Fetch all users from the user repository, not the role repository
        return userRepository.findAll();
    }

    @Override
    public Users findByUserNameAndPassword(String username, String password) {
        Optional<Users> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            // Check if the password matches
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null; // Return null if the user is not found or password doesn't match
    }

    @Override
    public boolean checkLogin(String username, String password) {
        Optional<Users> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            // Check if the password matches
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    @Override
    public UsersDTO addUser(UsersDTO usersDTO) {
        // Fetch the role (adjust the role ID based on your requirements)
        Role r = roleRepository.getById(2L); 
        
        Users u = new Users();
        u.setUsername(usersDTO.getUsername());
        u.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
        u.setRole(r);
        
        userRepository.save(u);
        
        return usersDTO;
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for email: " + email));
    }

    public boolean checkIfValidOldPassword(Users user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public void changeUserPassword(Users user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public void updatePassword(String username, String currentPassword, String newPassword) throws Exception {
        // Properly handle Optional<Users> returned by the repository method
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Check if the provided current password matches the one in the database
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new Exception("Current password is incorrect");
        }

        // Encode and set the new password
        user.setPassword(passwordEncoder.encode(newPassword));

        // Save the updated user object
        userRepository.save(user);
    }

    public void saveUser(Users user) {
        userRepository.save(user);
    }
}

