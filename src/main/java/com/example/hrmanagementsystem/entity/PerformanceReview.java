package com.example.hrmanagementsystem.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "performance_reviews")
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private LocalDate reviewDate;

    private String reviewPeriod;

    private Integer rating;

    private String feedback;

    private String reviewerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // Default Constructor
    public PerformanceReview() {
    }

    // Parameterized Constructor
    public PerformanceReview(Long reviewId, LocalDate reviewDate,
            String reviewPeriod, Integer rating,
            String feedback, String reviewerName,
            Employee employee) {

        this.reviewId = reviewId;
        this.reviewDate = reviewDate;
        this.reviewPeriod = reviewPeriod;
        this.rating = rating;
        this.feedback = feedback;
        this.reviewerName = reviewerName;
        this.employee = employee;
    }

    // Getters and Setters
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewPeriod() {
        return reviewPeriod;
    }

    public void setReviewPeriod(String reviewPeriod) {
        this.reviewPeriod = reviewPeriod;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
