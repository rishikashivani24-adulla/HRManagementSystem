package com.example.hrmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrmanagementsystem.entity.PerformanceReview;

public interface PerformanceReviewRepository
        extends JpaRepository<PerformanceReview, Long> {

    List<PerformanceReview> findByEmployeeEmployeeId(Long employeeId);

    List<PerformanceReview> findByRating(Integer rating);

    List<PerformanceReview> findByReviewPeriod(String reviewPeriod);

}
