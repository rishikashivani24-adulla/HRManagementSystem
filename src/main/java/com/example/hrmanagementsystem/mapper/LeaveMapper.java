package com.example.hrmanagementsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.hrmanagementsystem.dto.LeaveDTO;
import com.example.hrmanagementsystem.entity.Leave;

@Mapper(componentModel = "spring")
public interface LeaveMapper {

    @Mapping(target = "employeeId", source = "employee.employeeId")
    LeaveDTO toDTO(Leave leave);

    @Mapping(target = "employee.employeeId", source = "employeeId")
    Leave toEntity(LeaveDTO leaveDTO);
}
