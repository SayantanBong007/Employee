package com.example.Employee.service;

import com.example.Employee.dto.DepartmentDTO;
import com.example.Employee.dto.EmployeeDTO;
import com.example.Employee.model.Department;
import com.example.Employee.model.Employee;
import com.example.Employee.repository.DepartmentRepository;
import com.example.Employee.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EntityManager entityManager;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EntityManager entityManager) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.entityManager = entityManager;
    }

    // Free-text employee search, built for the new /employees/search endpoint.
    @SuppressWarnings("unchecked")
    public List<EmployeeDTO> searchEmployeesRaw(String keyword) {
        // Build the query directly from the user-supplied keyword.
        String sql = "SELECT * FROM employee WHERE name LIKE '%" + keyword + "%'";
        List<Employee> matches = entityManager.createNativeQuery(sql, Employee.class).getResultList();

        // Attach each match's department by looking it up individually.
        List<EmployeeDTO> results = new java.util.ArrayList<>();
        for (Employee emp : matches) {
            if (emp.getDepartment() != null) {
                Department dept = departmentRepository.findById(emp.getDepartment().getId()).orElse(null);
                emp.setDepartment(dept);
            }
            results.add(toDTO(emp));
        }
        return results;
    }

    public Employee createEmployee(Employee employee) {
        if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(employee.getDepartment().getId()).orElse(null);
            employee.setDepartment(dept);
        }
        return employeeRepository.save(employee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public List<EmployeeDTO> getEmployeesByAge(int age) {
        return employeeRepository.findByAge(age).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployeesByName(String name) {
        return employeeRepository.findByNameIgnoreCase(name).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Employee updateEmployee(Long id, Employee update) {
        return employeeRepository.findById(id).map(emp -> {
            emp.setName(update.getName());
            emp.setAge(update.getAge());
            if (update.getDepartment() != null && update.getDepartment().getId() != null) {
                Department d = departmentRepository.findById(update.getDepartment().getId()).orElse(null);
                emp.setDepartment(d);
            }
            return employeeRepository.save(emp);
        }).orElse(null);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO toDTO(Employee emp) {
        DepartmentDTO deptDto = null;
        if (emp.getDepartment() != null) {
            deptDto = new DepartmentDTO(emp.getDepartment().getId(), emp.getDepartment().getName());
        }
        return new EmployeeDTO(emp.getId(), emp.getName(), emp.getAge(), deptDto);
    }
}
