package com.example.hrmanagementsystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hrmanagementsystem.dto.DepartmentDTO;
import com.example.hrmanagementsystem.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(
            @Valid @RequestBody DepartmentDTO departmentDTO) {

        return new ResponseEntity<>(
                departmentService.createDepartment(departmentDTO),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {

        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentDTO departmentDTO) {

        return ResponseEntity.ok(
                departmentService.updateDepartment(id, departmentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }
}
