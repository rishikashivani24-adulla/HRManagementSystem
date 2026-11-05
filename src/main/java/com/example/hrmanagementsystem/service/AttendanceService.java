package com.example.hrmanagementsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hrmanagementsystem.dto.AttendanceDTO;
import com.example.hrmanagementsystem.entity.Attendance;
import com.example.hrmanagementsystem.entity.Employee;
import com.example.hrmanagementsystem.exception.ResourceNotFoundException;
import com.example.hrmanagementsystem.mapper.AttendanceMapper;
import com.example.hrmanagementsystem.repository.AttendanceRepository;
import com.example.hrmanagementsystem.repository.EmployeeRepository;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final AttendanceMapper attendanceMapper;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             EmployeeRepository employeeRepository,
                             AttendanceMapper attendanceMapper) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
        this.attendanceMapper = attendanceMapper;
    }

    // Create Attendance
    public AttendanceDTO createAttendance(AttendanceDTO attendanceDTO) {

        Employee employee = employeeRepository.findById(attendanceDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Attendance attendance = attendanceMapper.toEntity(attendanceDTO);
        attendance.setEmployee(employee);

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return attendanceMapper.toDTO(savedAttendance);
    }

    // Get All Attendance Records
    public List<AttendanceDTO> getAllAttendance() {

        return attendanceRepository.findAll()
                .stream()
                .map(attendanceMapper::toDTO)
                .toList();
    }

    // Get Attendance By ID
    public AttendanceDTO getAttendanceById(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        return attendanceMapper.toDTO(attendance);
    }

    // Update Attendance
    public AttendanceDTO updateAttendance(Long id, AttendanceDTO attendanceDTO) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        Employee employee = employeeRepository.findById(attendanceDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        attendance.setAttendanceDate(attendanceDTO.getAttendanceDate());
        attendance.setCheckInTime(attendanceDTO.getCheckInTime());
        attendance.setCheckOutTime(attendanceDTO.getCheckOutTime());
        attendance.setStatus(attendanceDTO.getStatus());
        attendance.setEmployee(employee);

        Attendance updatedAttendance = attendanceRepository.save(attendance);

        return attendanceMapper.toDTO(updatedAttendance);
    }

    // Delete Attendance
    public void deleteAttendance(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        attendanceRepository.delete(attendance);
    }

    // Get Attendance By Employee ID
    public List<AttendanceDTO> getAttendanceByEmployee(Long employeeId) {

        return attendanceRepository.findByEmployeeEmployeeId(employeeId)
                .stream()
                .map(attendanceMapper::toDTO)
                .toList();
    }

    // Get Attendance By Date
    public List<AttendanceDTO> getAttendanceByDate(
            java.time.LocalDate attendanceDate) {

        return attendanceRepository.findByAttendanceDate(attendanceDate)
                .stream()
                .map(attendanceMapper::toDTO)
                .toList();
    }
}