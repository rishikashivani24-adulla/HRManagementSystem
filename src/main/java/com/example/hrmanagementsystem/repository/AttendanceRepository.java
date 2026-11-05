package com.example.hrmanagementsystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrmanagementsystem.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployeeEmployeeId(Long employeeId);

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    List<Attendance> findByEmployeeEmployeeIdAndAttendanceDate(
            Long employeeId, LocalDate attendanceDate);

    List<Attendance> findByStatus(String status);

}
