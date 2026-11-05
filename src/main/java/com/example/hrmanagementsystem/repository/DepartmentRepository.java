package com.example.hrmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hrmanagementsystem.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByDepartmentName(String departmentName);

}
