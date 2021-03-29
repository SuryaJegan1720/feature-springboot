package com.example.springdatajpa.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springdatajpa.model.Student;

public interface StudentRepos extends JpaRepository<Student, Long> {


}
