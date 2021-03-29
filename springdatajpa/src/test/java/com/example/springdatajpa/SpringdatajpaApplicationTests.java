package com.example.springdatajpa;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springdatajpa.model.Student;
import com.example.springdatajpa.repos.StudentRepos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringdatajpaApplicationTests {
	@Autowired
	public StudentRepos studentRepos;

	@Test
	public void testSaveData() {
		Student student = new Student();
		student.setId(1L);
		student.setName("SuryaJegan");
		student.setTestScore(80);

		// Create
		studentRepos.save(student);

		// READ
		Student savedStudent = studentRepos.findById(1L).get();
		assertNotNull(savedStudent);
		assertEquals(student.getTestScore(), savedStudent.getTestScore());

		// UPDATE
		student.setTestScore(70);
		studentRepos.save(student);
		
		Student updatedStudent = studentRepos.findById(1L).get();
		assertNotNull(updatedStudent);
		assertEquals(70, updatedStudent.getTestScore());
		
		//DELETE
		studentRepos.delete(student);
		assertNotNull(student);
		

	}

}
