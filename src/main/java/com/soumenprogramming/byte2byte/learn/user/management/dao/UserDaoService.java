package com.soumenprogramming.byte2byte.learn.user.management.dao;


import com.soumenprogramming.byte2byte.learn.user.management.entity.UserRegistration;
import com.soumenprogramming.byte2byte.learn.user.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDaoService {

    @Autowired
    UserRepository userRepository;

    public UserRegistration findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public UserRegistration save(UserRegistration user) {
        return userRepository.save(user);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
