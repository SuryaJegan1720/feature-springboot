package com.sample.test.service;

import com.sample.test.model.Employee;
import com.sample.test.repos.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    //Get all
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    // CREATE
    public Employee createEmployee(Employee employee) {
        if (employee != null) {
            if (employee.getCreateAt() == null)
                employee.setCreateAt(LocalDateTime.now());
            return employeeRepository.save(employee);
        }
        return null;
    }

    // READ BY ID
    public Employee getEmployeeById(Integer id) {
         Optional<Employee> result = employeeRepository.findById(id);
        return result.orElse(null);
    }

    // UPDATE
    public Employee updateEmployee(Integer id, Employee employee) {

        Employee existingEmployee =
                employeeRepository.findById(id).orElse(null);

        if (existingEmployee == null) {
            return null;
        }

        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setCreateAt(employee.getCreateAt());

        employeeRepository.save(existingEmployee);
        return existingEmployee;
    }

    // DELETE
    public boolean deleteEmployee(Integer id) {

        Employee employee =
                employeeRepository.findById(id).orElse(null);

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
