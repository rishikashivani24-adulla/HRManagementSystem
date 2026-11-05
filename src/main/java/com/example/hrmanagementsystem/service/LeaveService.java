package com.example.hrmanagementsystem.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hrmanagementsystem.dto.LeaveDTO;
import com.example.hrmanagementsystem.entity.Employee;
import com.example.hrmanagementsystem.entity.Leave;
import com.example.hrmanagementsystem.exception.ResourceNotFoundException;
import com.example.hrmanagementsystem.mapper.LeaveMapper;
import com.example.hrmanagementsystem.repository.EmployeeRepository;
import com.example.hrmanagementsystem.repository.LeaveRepository;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveMapper leaveMapper;

    public LeaveService(LeaveRepository leaveRepository,
                        EmployeeRepository employeeRepository,
                        LeaveMapper leaveMapper) {
        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
        this.leaveMapper = leaveMapper;
    }

    // Create Leave
    public LeaveDTO createLeave(LeaveDTO leaveDTO) {

        Employee employee = employeeRepository.findById(leaveDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Leave leave = leaveMapper.toEntity(leaveDTO);
        leave.setEmployee(employee);
        leave.setAppliedDate(LocalDate.now());

        Leave savedLeave = leaveRepository.save(leave);

        return leaveMapper.toDTO(savedLeave);
    }

    // Get All Leaves
    public List<LeaveDTO> getAllLeaves() {

        return leaveRepository.findAll()
                .stream()
                .map(leaveMapper::toDTO)
                .toList();
    }

    // Get Leave By ID
    public LeaveDTO getLeaveById(Long id) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave record not found"));

        return leaveMapper.toDTO(leave);
    }

    // Update Leave
    public LeaveDTO updateLeave(Long id, LeaveDTO leaveDTO) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave record not found"));

        Employee employee = employeeRepository.findById(leaveDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        leave.setLeaveType(leaveDTO.getLeaveType());
        leave.setStartDate(leaveDTO.getStartDate());
        leave.setEndDate(leaveDTO.getEndDate());
        leave.setReason(leaveDTO.getReason());
        leave.setStatus(leaveDTO.getStatus());
        leave.setEmployee(employee);

        Leave updatedLeave = leaveRepository.save(leave);

        return leaveMapper.toDTO(updatedLeave);
    }

    // Delete Leave
    public void deleteLeave(Long id) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave record not found"));

        leaveRepository.delete(leave);
    }

    // Get Leaves By Employee ID
    public List<LeaveDTO> getLeavesByEmployee(Long employeeId) {

        return leaveRepository.findByEmployeeEmployeeId(employeeId)
                .stream()
                .map(leaveMapper::toDTO)
                .toList();
    }

    // Get Leaves By Status
    public List<LeaveDTO> getLeavesByStatus(String status) {

        return leaveRepository.findByStatus(status)
                .stream()
                .map(leaveMapper::toDTO)
                .toList();
    }
}