package com.example.hrmanagementsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.hrmanagementsystem.dto.EmployeeDTO;
import com.example.hrmanagementsystem.entity.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "departmentId", source = "department.departmentId")
    EmployeeDTO toDTO(Employee employee);

    @Mapping(target = "department.departmentId", source = "departmentId")
    Employee toEntity(EmployeeDTO employeeDTO);
}
