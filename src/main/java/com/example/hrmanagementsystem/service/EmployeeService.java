package com.example.hrmanagementsystem.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hrmanagementsystem.dto.EmployeeDTO;
import com.example.hrmanagementsystem.entity.Department;
import com.example.hrmanagementsystem.entity.Employee;
import com.example.hrmanagementsystem.exception.ResourceNotFoundException;
import com.example.hrmanagementsystem.mapper.EmployeeMapper;
import com.example.hrmanagementsystem.repository.DepartmentRepository;
import com.example.hrmanagementsystem.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            EmployeeMapper employeeMapper) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    // Create Employee
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        logger.info("Creating employee with email: {}",
                employeeDTO.getEmail());

        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {

            logger.error(
                    "Employee creation failed. Email already exists: {}",
                    employeeDTO.getEmail());

            throw new RuntimeException("Email already exists");
        }

        Employee employee = employeeMapper.toEntity(employeeDTO);

        if (employeeDTO.getDepartmentId() != null) {

            Department department = departmentRepository
                    .findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> {

                        logger.error("Department not found: {}",
                                employeeDTO.getDepartmentId());

                        return new ResourceNotFoundException(
                                "Department not found");
                    });

            employee.setDepartment(department);
        }

        Employee savedEmployee = employeeRepository.save(employee);

        logger.info("Employee created successfully with ID: {}",
                savedEmployee.getEmployeeId());

        return employeeMapper.toDTO(savedEmployee);
    }

    // Get All Employees
    public List<EmployeeDTO> getAllEmployees() {

        logger.info("Fetching all employees");

        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    // Pagination
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {

        logger.info("Fetching employees with pagination: {}",
                pageable);

        return employeeRepository.findAll(pageable)
                .map(employeeMapper::toDTO);
    }

    // Sorting
    public List<EmployeeDTO> getAllEmployeesSorted(Sort sort) {

        logger.info("Fetching employees with sorting: {}",
                sort);

        return employeeRepository.findAll(sort)
                .stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    // Get Employee By ID
    @Cacheable("employees")
    public EmployeeDTO getEmployeeById(Long id) {

        logger.info("Fetching employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Employee not found with ID: {}", id);

                    return new ResourceNotFoundException(
                            "Employee not found with ID: " + id);
                });

        return employeeMapper.toDTO(employee);
    }

    // Update Employee
    public EmployeeDTO updateEmployee(
            Long id,
            EmployeeDTO employeeDTO) {

        logger.info("Updating employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Employee not found with ID: {}", id);

                    return new ResourceNotFoundException(
                            "Employee not found with ID: " + id);
                });

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhone(employeeDTO.getPhone());
        employee.setDesignation(employeeDTO.getDesignation());
        employee.setSalary(employeeDTO.getSalary());
        employee.setJoiningDate(employeeDTO.getJoiningDate());
        employee.setStatus(employeeDTO.getStatus());

        if (employeeDTO.getDepartmentId() != null) {

            Department department = departmentRepository
                    .findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> {

                        logger.error("Department not found: {}",
                                employeeDTO.getDepartmentId());

                        return new ResourceNotFoundException(
                                "Department not found");
                    });

            employee.setDepartment(department);

        } else {

            employee.setDepartment(null);
        }

        Employee updatedEmployee =
                employeeRepository.save(employee);

        logger.info("Employee updated successfully with ID: {}",
                id);

        return employeeMapper.toDTO(updatedEmployee);
    }

    // Delete Employee
    public void deleteEmployee(Long id) {

        logger.info("Deleting employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Employee not found with ID: {}", id);

                    return new ResourceNotFoundException(
                            "Employee not found with ID: " + id);
                });

        employeeRepository.delete(employee);

        logger.info("Employee deleted successfully with ID: {}",
                id);
    }

    // Get Employees By Department
    public List<EmployeeDTO> getEmployeesByDepartment(
            Long departmentId) {

        logger.info(
                "Fetching employees for department ID: {}",
                departmentId);

        return employeeRepository
                .findByDepartmentDepartmentId(departmentId)
                .stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    // Get Employees By Status
    public List<EmployeeDTO> getEmployeesByStatus(
            String status) {

        logger.info(
                "Fetching employees with status: {}",
                status);

        return employeeRepository
                .findByStatus(status)
                .stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    // JPQL
    public List<EmployeeDTO> getEmployeesWithSalaryGreaterThan(
            BigDecimal salary) {

        logger.info(
                "Fetching employees with salary greater than: {}",
                salary);

        return employeeRepository
                .findEmployeesWithSalaryGreaterThan(salary)
                .stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    // Native SQL
    public List<EmployeeDTO> getEmployeesBySalaryNative(
            BigDecimal salary) {

        logger.info(
                "Fetching employees using native SQL with salary: {}",
                salary);

        return employeeRepository
                .findEmployeesBySalaryNative(salary)
                .stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    // Named Query
    public List<EmployeeDTO> getEmployeesByDesignation(
            String designation) {

        logger.info(
                "Fetching employees with designation: {}",
                designation);

        return employeeRepository
                .findByDesignation(designation)
                .stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    // Scheduler
    @Scheduled(fixedRate = 30000)
    public void scheduledTask() {

        logger.info("Scheduled task is running...");
    }
}
