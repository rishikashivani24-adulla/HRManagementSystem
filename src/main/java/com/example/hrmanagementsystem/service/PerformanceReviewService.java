package com.example.hrmanagementsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hrmanagementsystem.dto.PerformanceReviewDTO;
import com.example.hrmanagementsystem.entity.Employee;
import com.example.hrmanagementsystem.entity.PerformanceReview;
import com.example.hrmanagementsystem.exception.ResourceNotFoundException;
import com.example.hrmanagementsystem.mapper.PerformanceReviewMapper;
import com.example.hrmanagementsystem.repository.EmployeeRepository;
import com.example.hrmanagementsystem.repository.PerformanceReviewRepository;

@Service
public class PerformanceReviewService {

    private final PerformanceReviewRepository performanceReviewRepository;
    private final EmployeeRepository employeeRepository;
    private final PerformanceReviewMapper performanceReviewMapper;

    public PerformanceReviewService(
            PerformanceReviewRepository performanceReviewRepository,
            EmployeeRepository employeeRepository,
            PerformanceReviewMapper performanceReviewMapper) {

        this.performanceReviewRepository = performanceReviewRepository;
        this.employeeRepository = employeeRepository;
        this.performanceReviewMapper = performanceReviewMapper;
    }

    // Create Performance Review
    public PerformanceReviewDTO createReview(PerformanceReviewDTO reviewDTO) {

        Employee employee = employeeRepository.findById(reviewDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        PerformanceReview review = performanceReviewMapper.toEntity(reviewDTO);
        review.setEmployee(employee);

        PerformanceReview savedReview = performanceReviewRepository.save(review);

        return performanceReviewMapper.toDTO(savedReview);
    }

    // Get All Performance Reviews
    public List<PerformanceReviewDTO> getAllReviews() {

        return performanceReviewRepository.findAll()
                .stream()
                .map(performanceReviewMapper::toDTO)
                .toList();
    }

    // Get Performance Review By ID
    public PerformanceReviewDTO getReviewById(Long id) {

        PerformanceReview review = performanceReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Performance review not found"));

        return performanceReviewMapper.toDTO(review);
    }

    // Update Performance Review
    public PerformanceReviewDTO updateReview(Long id, PerformanceReviewDTO reviewDTO) {

        PerformanceReview review = performanceReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Performance review not found"));

        Employee employee = employeeRepository.findById(reviewDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        review.setReviewDate(reviewDTO.getReviewDate());
        review.setReviewPeriod(reviewDTO.getReviewPeriod());
        review.setRating(reviewDTO.getRating());
        review.setFeedback(reviewDTO.getFeedback());
        review.setReviewerName(reviewDTO.getReviewerName());
        review.setEmployee(employee);

        PerformanceReview updatedReview = performanceReviewRepository.save(review);

        return performanceReviewMapper.toDTO(updatedReview);
    }

    // Delete Performance Review
    public void deleteReview(Long id) {

        PerformanceReview review = performanceReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Performance review not found"));

        performanceReviewRepository.delete(review);
    }

    // Get Reviews By Employee ID
    public List<PerformanceReviewDTO> getReviewsByEmployee(Long employeeId) {

        return performanceReviewRepository.findByEmployeeEmployeeId(employeeId)
                .stream()
                .map(performanceReviewMapper::toDTO)
                .toList();
    }

    // Get Reviews By Rating
    public List<PerformanceReviewDTO> getReviewsByRating(Integer rating) {

        return performanceReviewRepository.findByRating(rating)
                .stream()
                .map(performanceReviewMapper::toDTO)
                .toList();
    }
}