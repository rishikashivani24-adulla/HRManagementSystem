package com.example.hrmanagementsystem.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrmanagementsystem.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find Employee By Email
    Optional<Employee> findByEmail(String email);

    // Check Employee Email
    boolean existsByEmail(String email);

    // Find Employees By Department ID
    List<Employee> findByDepartmentDepartmentId(Long departmentId);

    // Find Employees By Status
    List<Employee> findByStatus(String status);

    // JPQL Query: Find Employees With Salary Greater Than
    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    List<Employee> findEmployeesWithSalaryGreaterThan(
            @Param("salary") BigDecimal salary);

    // Native SQL Query: Find Employees With Salary Greater Than
    @Query(value = "SELECT * FROM employees WHERE salary > :salary",
           nativeQuery = true)
    List<Employee> findEmployeesBySalaryNative(
            @Param("salary") BigDecimal salary);

    // Named Query: Find Employees By Designation
    List<Employee> findByDesignation(String designation);
}