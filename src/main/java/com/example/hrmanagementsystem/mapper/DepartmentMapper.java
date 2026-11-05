package com.example.hrmanagementsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.hrmanagementsystem.dto.DepartmentDTO;
import com.example.hrmanagementsystem.entity.Department;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDTO toDTO(Department department);

    @Mapping(target = "employees", ignore = true)
    Department toEntity(DepartmentDTO departmentDTO);
}
