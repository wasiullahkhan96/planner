package com.example.planner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.planner.models.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    // You can add custom queries if needed, e.g.
    // List<Activity> findByLocation(String location);
}
