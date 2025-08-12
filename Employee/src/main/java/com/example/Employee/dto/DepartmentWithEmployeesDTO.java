package com.example.Employee.dto;

import java.util.List;

public class DepartmentWithEmployeesDTO {
    private Long id;
    private String name;
    private List<String> employees; // list of employee names only

    public DepartmentWithEmployeesDTO() {}

    public DepartmentWithEmployeesDTO(Long id, String name, List<String> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getEmployees() { return employees; }
    public void setEmployees(List<String> employees) { this.employees = employees; }
}
