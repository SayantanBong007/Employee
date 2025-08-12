package com.example.Employee.controller;

import com.example.Employee.dto.DepartmentWithEmployeesDTO;
import com.example.Employee.model.Department;
import com.example.Employee.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // create
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department dept) {
        Department created = departmentService.createDepartment(dept);
        return ResponseEntity.ok(created);
    }

    // get all departments with list of employee NAMES only
    @GetMapping
    public ResponseEntity<List<DepartmentWithEmployeesDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    // get single department with employee names
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentWithEmployeesDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentWithEmployeesDTO dto = departmentService.getDepartmentById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }
}
