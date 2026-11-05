package com.example.hrmanagementsystem.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.example.hrmanagementsystem.dto.EmployeeDTO;
import com.example.hrmanagementsystem.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Create Employee
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(
            @Valid @RequestBody EmployeeDTO employeeDTO) {

        EmployeeDTO savedEmployee = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // Get All Employees
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {

        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // Get Employee By ID
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // Update Employee
    @PutMapping("/{id:\\d+}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO employeeDTO) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(id, employeeDTO));
    }

    // Delete Employee
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // Get Employees By Department ID
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(
            @PathVariable Long departmentId) {

        return ResponseEntity.ok(
                employeeService.getEmployeesByDepartment(departmentId));
    }

    // Get Employees By Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                employeeService.getEmployeesByStatus(status));
    }

    // JPQL: Salary Greater Than
    @GetMapping("/salary-greater-than/{salary}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesWithSalaryGreaterThan(
            @PathVariable BigDecimal salary) {

        return ResponseEntity.ok(
                employeeService.getEmployeesWithSalaryGreaterThan(salary));
    }

    // Native SQL: Salary Greater Than
    @GetMapping("/native-salary-greater-than/{salary}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesBySalaryNative(
            @PathVariable BigDecimal salary) {

        return ResponseEntity.ok(
                employeeService.getEmployeesBySalaryNative(salary));
    }

    // Named Query: Employees By Designation
    @GetMapping("/designation/{designation}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDesignation(
            @PathVariable String designation) {

        return ResponseEntity.ok(
                employeeService.getEmployeesByDesignation(designation));
    }

    // Pagination
    @GetMapping("/pagination")
    public ResponseEntity<Page<EmployeeDTO>> getEmployeesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                employeeService.getAllEmployees(pageable));
    }

    // Sorting
    @GetMapping("/sorting")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesWithSorting(
            @RequestParam(defaultValue = "employeeId") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return ResponseEntity.ok(
                employeeService.getAllEmployeesSorted(sort));
    }
}