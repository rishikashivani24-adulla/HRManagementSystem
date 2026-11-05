package com.example.hrmanagementsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.hrmanagementsystem.dto.AttendanceDTO;
import com.example.hrmanagementsystem.entity.Attendance;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(target = "employeeId", source = "employee.employeeId")
    AttendanceDTO toDTO(Attendance attendance);

    @Mapping(target = "employee.employeeId", source = "employeeId")
    Attendance toEntity(AttendanceDTO attendanceDTO);
}
