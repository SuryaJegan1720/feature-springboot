package com.example.demo.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.demo.service.dao.Arithmetic;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloFirstSpringbootApplicationTests {

	@Autowired
	 private Arithmetic arithmetic;
	@Test
	public void testAdditionNumbers() {
		assertEquals(5, arithmetic.addition(3, 2));		
	}

}
