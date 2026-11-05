package com.example.hrmanagementsystem.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hrmanagementsystem.dto.AttendanceDTO;
import com.example.hrmanagementsystem.service.AttendanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> createAttendance(
            @Valid @RequestBody AttendanceDTO attendanceDTO) {

        return new ResponseEntity<>(
                attendanceService.createAttendance(attendanceDTO),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAllAttendance() {

        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDTO> getAttendanceById(
            @PathVariable Long id) {

        return ResponseEntity.ok(attendanceService.getAttendanceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(
            @PathVariable Long id,
            @Valid @RequestBody AttendanceDTO attendanceDTO) {

        return ResponseEntity.ok(
                attendanceService.updateAttendance(id, attendanceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {

        attendanceService.deleteAttendance(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByEmployee(employeeId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByDate(
            @PathVariable LocalDate date) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByDate(date));
    }
}
