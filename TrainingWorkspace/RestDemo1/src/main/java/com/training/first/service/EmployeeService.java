package com.training.first.service;

import org.springframework.stereotype.Service;

import com.training.first.model.Employee;
import com.training.first.model.Employees;

@Service
public class EmployeeService {
	private static Employees list = new Employees();
    
    static 
    {
        list.getEmployeeList().add(new Employee(1, "John", "Mathew", "john@gmail.com"));
        list.getEmployeeList().add(new Employee(2, "Kumar", "Ravi", "kumar@gmail.com"));
        list.getEmployeeList().add(new Employee(3, "Shameem", "Ali", "shameem@gmail.com"));
    }
    
    public Employees getAllEmployees() 
    {
        return list;
    }
    
    public void addEmployee(Employee employee) {
        list.getEmployeeList().add(employee);
    }

}
