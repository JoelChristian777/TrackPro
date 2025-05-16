package com.trackpro.fitness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackpro.fitness.model.ExerciseLog;
import com.trackpro.fitness.model.User;

public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {
    List<ExerciseLog> findByUser(User user);
}
