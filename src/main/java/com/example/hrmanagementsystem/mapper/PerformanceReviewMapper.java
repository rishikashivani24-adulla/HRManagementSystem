package com.example.hrmanagementsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.hrmanagementsystem.dto.PerformanceReviewDTO;
import com.example.hrmanagementsystem.entity.PerformanceReview;

@Mapper(componentModel = "spring")
public interface PerformanceReviewMapper {

    @Mapping(target = "employeeId", source = "employee.employeeId")
    PerformanceReviewDTO toDTO(PerformanceReview review);

    @Mapping(target = "employee.employeeId", source = "employeeId")
    PerformanceReview toEntity(PerformanceReviewDTO reviewDTO);
}
