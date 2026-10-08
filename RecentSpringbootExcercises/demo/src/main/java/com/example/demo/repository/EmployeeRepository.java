package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>(
            List.of(
                    new Employee(1, "Surya", "surya@gmail.com", "IT"),
                    new Employee(2, "John", "john@gmail.com", "Finance"),
                    new Employee(3, "David", "david@gmail.com", "HR"),
                    new Employee(4, "Raj", "raj@gmail.com", "IT"),
                    new Employee(5, "Arun", "arun@gmail.com", "Sales"),
                    new Employee(6, "Kumar", "kumar@gmail.com", "Finance"),
                    new Employee(7, "Priya", "priya@gmail.com", "HR"),
                    new Employee(8, "Anand", "anand@gmail.com", "IT"),
                    new Employee(9, "Vijay", "vijay@gmail.com", "Sales"),
                    new Employee(10, "Suresh", "suresh@gmail.com", "IT")
            )
    );



    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee findById(Integer id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Integer id) {
        employees.removeIf(employee ->
                employee.getId().equals(id));
    }

    public List<Employee> findByDepartment(String department) {
        return employees.stream()
                .filter(employee ->
                        employee.getDepartment().equalsIgnoreCase(department))
                .toList();
    }

}
