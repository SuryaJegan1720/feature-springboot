package com.example.employee_management.service;

import com.example.employee_management.entity.Employee;
import com.example.employee_management.repository.EmployeeRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // CREATE
    public Employee createEmployee(Employee employee) {
        employee.setId(null);
        return employeeRepository.save(employee);
    }

    // READ ALL
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // READ BY ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found: " + id));
    }

    // UPDATE
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = getEmployeeById(id);

        existing.setName(updatedEmployee.getName());
        existing.setGender(updatedEmployee.getGender());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setAge(updatedEmployee.getAge());
        existing.setSalary(updatedEmployee.getSalary());
        existing.setYearOfJoining(updatedEmployee.getYearOfJoining());

        return employeeRepository.save(existing);
    }

    // DELETE
    public void deleteEmployee(Long id) {
        Employee existing = getEmployeeById(id);
        employeeRepository.delete(existing);
    }

    // 1. How many male and female employees?
    public Map<String, Long> countByGender() {
        List<Employee> employeeList = getAllEmployees();

        return employeeList.stream()
                .collect(Collectors.groupingBy(
                        Employee::getGender,
                        Collectors.counting()
                ));
    }

    // 2. Print all distinct department names
    public List<String> getDepartmentNames() {
        List<Employee> employeeList = getAllEmployees();

        return employeeList.stream()
                .map(Employee::getDepartment)
                .filter(department -> department != null)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    // 3. Average age of male and female employees
    public Map<String, Double> averageAgeByGender() {
        List<Employee> employeeList = getAllEmployees();

        return employeeList.stream()
                .collect(Collectors.groupingBy(
                        Employee::getGender,
                        Collectors.averagingDouble(Employee::getAge)
                ));
    }

    // 4. Highest-paid employee
    public Optional<Employee> getHighestPaidEmployee() {
        List<Employee> employeeList = getAllEmployees();

        return employeeList.stream()
                .max((e1, e2) ->
                        Double.compare(e1.getSalary(), e2.getSalary()));
    }

    // 5. Employees who joined strictly after 2015
    public List<String> getEmployeesJoinedAfter2015() {
        List<Employee> employeeList = getAllEmployees();

        return employeeList.stream()
                .filter(emp -> emp.getYearOfJoining() > 2015)
                .map(Employee::getName)
                .collect(Collectors.toList());
    }

    // 6. Count employees in each department
    public Map<String, Long> countByDepartment() {
        List<Employee> employeeList = getAllEmployees();

        return employeeList.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.counting()
                ));
    }
}
