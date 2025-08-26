package com.soumenprogramming.byte2byte.learn.user.management.repository;

import com.soumenprogramming.byte2byte.learn.user.management.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserRegistration, Long> {
    Optional<UserRegistration> findByUsername(String username);
    
    Optional<UserRegistration> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}
