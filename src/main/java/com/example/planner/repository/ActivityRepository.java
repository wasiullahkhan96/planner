package com.example.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.planner.model.Activity;
import java.util.*;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    
    // Add method to find an activity by name
    Optional<Activity> findByName(String name);
}
