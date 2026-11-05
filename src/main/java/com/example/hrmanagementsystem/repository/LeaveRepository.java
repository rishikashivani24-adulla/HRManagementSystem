package com.example.hrmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrmanagementsystem.entity.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployeeEmployeeId(Long employeeId);

    List<Leave> findByStatus(String status);

    List<Leave> findByLeaveType(String leaveType);

}
