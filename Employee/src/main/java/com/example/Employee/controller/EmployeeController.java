package com.example.Employee.controller;

import com.example.Employee.dto.EmployeeDTO;
import com.example.Employee.model.Employee;
import com.example.Employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // create employee (provide department: { "id": X })
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee created = employeeService.createEmployee(employee);
        return ResponseEntity.ok(created);
    }

    // get all employees (DTO: department only id & name)
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // get employee by id (DTO)
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO dto = employeeService.getEmployeeById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByAge(@PathVariable int age) {
        List<EmployeeDTO> dtos = employeeService.getEmployeesByAge(age);
        if (dtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByName(@PathVariable String name) {
        List<EmployeeDTO> dtos = employeeService.getEmployeesByName(name);
        if (dtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dtos);
    }

    // free-text search across employee names
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDTO>> searchEmployees(@RequestParam String keyword) {
        List<EmployeeDTO> dtos = employeeService.searchEmployeesRaw(keyword);
        return ResponseEntity.ok(dtos);
    }


    // get employees by department id (DTO list)
    @GetMapping("/department/{deptId}")
    public ResponseEntity<List<EmployeeDTO>> getByDepartment(@PathVariable Long deptId) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartmentId(deptId));
    }

    // update employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee update) {
        Employee updated = employeeService.updateEmployee(id, update);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
