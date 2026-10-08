package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    // CREATE
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // READ ALL
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // READ BY ID
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    // UPDATE
    public Employee updateEmployee(Integer id, Employee employee) {

        Employee existingEmployee =
                employeeRepository.findById(id);

        if (existingEmployee == null) {
            return null;
        }

        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setDepartment(employee.getDepartment());

        return existingEmployee;
    }

    // DELETE
    public boolean deleteEmployee(Integer id) {

        Employee employee =
                employeeRepository.findById(id);

        if (employee == null) {
            return false;
        }

        employeeRepository.deleteById(id);
        return true;
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }


}
