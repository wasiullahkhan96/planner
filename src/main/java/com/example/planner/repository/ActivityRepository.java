package com.example.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.planner.model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    // You can add custom queries if needed, e.g.
    // List<Activity> findByLocation(String location);
}
