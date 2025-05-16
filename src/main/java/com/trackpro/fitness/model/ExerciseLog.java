package com.trackpro.fitness.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ExerciseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate logDate;

    private int benchPress;
    private int deadlift;
    private int squats;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters & Setters
    public Long getId() { return id; }

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public int getBenchPress() { return benchPress; }
    public void setBenchPress(int benchPress) { this.benchPress = benchPress; }

    public int getDeadlift() { return deadlift; }
    public void setDeadlift(int deadlift) { this.deadlift = deadlift; }

    public int getSquats() { return squats; }
    public void setSquats(int squats) { this.squats = squats; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
