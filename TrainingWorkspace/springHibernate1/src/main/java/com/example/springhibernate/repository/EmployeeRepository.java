package com.example.springhibernate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.springhibernate.model.EmployeeEntity;


@Repository
public interface EmployeeRepository 
			extends CrudRepository<EmployeeEntity, Long> {

}
