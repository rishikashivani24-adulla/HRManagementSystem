package com.example.hrmanagementsystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hrmanagementsystem.dto.LeaveDTO;
import com.example.hrmanagementsystem.service.LeaveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping
    public ResponseEntity<LeaveDTO> createLeave(
            @Valid @RequestBody LeaveDTO leaveDTO) {

        return new ResponseEntity<>(
                leaveService.createLeave(leaveDTO),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LeaveDTO>> getAllLeaves() {

        return ResponseEntity.ok(leaveService.getAllLeaves());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveDTO> getLeaveById(
            @PathVariable Long id) {

        return ResponseEntity.ok(leaveService.getLeaveById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveDTO> updateLeave(
            @PathVariable Long id,
            @Valid @RequestBody LeaveDTO leaveDTO) {

        return ResponseEntity.ok(
                leaveService.updateLeave(id, leaveDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {

        leaveService.deleteLeave(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveDTO>> getLeavesByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                leaveService.getLeavesByEmployee(employeeId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeaveDTO>> getLeavesByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                leaveService.getLeavesByStatus(status));
    }
}
