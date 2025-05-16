package com.trackpro.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackpro.fitness.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
