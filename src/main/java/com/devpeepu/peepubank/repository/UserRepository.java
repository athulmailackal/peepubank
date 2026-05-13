package com.devpeepu.peepubank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpeepu.peepubank.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    

    
}
