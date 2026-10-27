package com.example.employee_management.controller;

import com.example.employee_management.entity.Employee;
import com.example.employee_management.service.EmployeeService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    // READ ALL
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // READ ONE
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    // 1. Count male and female employees
    @GetMapping("/stats/gender")
    public Map<String, Long> countByGender() {
        return employeeService.countByGender();
    }

    // 2. Department names
    @GetMapping("/stats/departments")
    public List<String> getDepartmentNames() {
        return employeeService.getDepartmentNames();
    }

    // 3. Average age by gender
    @GetMapping("/stats/average-age")
    public Map<String, Double> averageAgeByGender() {
        return employeeService.averageAgeByGender();
    }

    // 4. Highest-paid employee
    @GetMapping("/stats/highest-paid")
    public Employee getHighestPaidEmployee() {
        return employeeService.getHighestPaidEmployee()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No employees found"));
    }

    // 5. Names of employees who joined after 2015
    @GetMapping("/stats/joined-after-2015")
    public List<String> getEmployeesJoinedAfter2015() {
        return employeeService.getEmployeesJoinedAfter2015();
    }

    // 6. Employee count by department
    @GetMapping("/stats/department-count")
    public Map<String, Long> countByDepartment() {
        return employeeService.countByDepartment();
    }
}
