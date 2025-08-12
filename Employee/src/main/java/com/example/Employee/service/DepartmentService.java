package com.example.Employee.service;

import com.example.Employee.dto.DepartmentWithEmployeesDTO;
import com.example.Employee.model.Department;
import com.example.Employee.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<DepartmentWithEmployeesDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(d -> new DepartmentWithEmployeesDTO(
                        d.getId(),
                        d.getName(),
                        d.getEmployees() == null ? List.of()
                                : d.getEmployees().stream().map(e -> e.getName()).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public DepartmentWithEmployeesDTO getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(d -> new DepartmentWithEmployeesDTO(
                        d.getId(),
                        d.getName(),
                        d.getEmployees() == null ? List.of() : d.getEmployees().stream().map(e -> e.getName()).collect(Collectors.toList())
                ))
                .orElse(null);
    }
}
