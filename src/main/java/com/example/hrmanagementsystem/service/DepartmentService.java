package com.example.hrmanagementsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hrmanagementsystem.dto.DepartmentDTO;
import com.example.hrmanagementsystem.entity.Department;
import com.example.hrmanagementsystem.exception.ResourceNotFoundException;
import com.example.hrmanagementsystem.mapper.DepartmentMapper;
import com.example.hrmanagementsystem.repository.DepartmentRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository,
                             DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    // Create Department
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {

        if (departmentRepository.existsByDepartmentName(departmentDTO.getDepartmentName())) {
            throw new RuntimeException("Department already exists");
        }

        Department department = departmentMapper.toEntity(departmentDTO);

        Department savedDepartment = departmentRepository.save(department);

        return departmentMapper.toDTO(savedDepartment);
    }

    // Get All Departments
    public List<DepartmentDTO> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDTO)
                .toList();
    }

    // Get Department By ID
    public DepartmentDTO getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        return departmentMapper.toDTO(department);
    }

    // Update Department
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        department.setDepartmentName(departmentDTO.getDepartmentName());
        department.setDescription(departmentDTO.getDescription());

        Department updatedDepartment = departmentRepository.save(department);

        return departmentMapper.toDTO(updatedDepartment);
    }

    // Delete Department
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        departmentRepository.delete(department);
    }
}