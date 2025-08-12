package com.example.Employee.repository;

import com.example.Employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);

    // NEW: Find employees by age
    List<Employee> findByAge(int age);

    // NEW: Find employees by name
    List<Employee> findByNameIgnoreCase(String name);
}
